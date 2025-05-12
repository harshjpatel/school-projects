import Ember from 'ember';

export default Ember.Route.extend({
  deleteUserID: null,

  model() {
    //route retrives all users from api and passes them to setupController
    return this.get('store').findAll('user');
  },
  setupController: function(controller, model) {
    //this takes the route's model and allows the template to gain access
      this._super(controller, model);
      controller.set('model', model);
      controller.set('isUser', true);
  },
  actions: {
    deleteModel() {
      let $this = this;
      let id = this.get('deleteUserID');
      console.log("Deleting User with ID "+id);
      this.get('store').findRecord('user',id, {backgroundReload: false}).then(function(user){
        user.destroyRecord().then(function(){
          $this.refresh();
        });
      });
      console.log("Record Deleted");
      Ember.$('.confirm-delete').css({visibility:'hidden'});
      Ember.$('.notification-delete').fadeIn(400, function() {
        Ember.run.later(this,function() {
          Ember.$('.notification-delete').fadeOut(1500);
        },1500);
      });
    },
    cancelDeleteModel() {
      let confirmDelete = Ember.$('.confirm-delete');
      confirmDelete.css({visibility:'hidden'});
      this.set('deleteUserID',null);
    },
    addUser() {
      this.transitionTo('main.euser', null);
    },
    confirmDelete(id) {
      console.log('deleted id is '+id);
      Ember.$('.confirm-delete').css({visibility:'visible'});
      this.set('deleteUserID',id);
    },
    editUser(id) {
      this.transitionTo('main.euser', id);
    }
}
});
