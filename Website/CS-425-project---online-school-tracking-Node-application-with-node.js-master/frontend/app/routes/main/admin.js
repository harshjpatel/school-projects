import Ember from 'ember';

export default Ember.Route.extend({
  deleteAdminID: null,

  model() {
    //route retrives all admins from api and passes them to setupController
    return this.get('store').findAll('admin');
  },
  setupController: function(controller, model) {
    //this takes the route's model and allows the template to gain access
      this._super(controller, model);
      controller.set('model', model);
      controller.set('isAdmin', true);
  },
  actions: {
    deleteModel() {
      let $this = this;
      let id = this.get('deleteAdminID');
      console.log("Deleting Admin with ID "+id);
      this.get('store').findRecord('admin',id, {backgroundReload: false}).then(function(admin){
        admin.destroyRecord().then(function(){
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
      this.set('deleteAdminID',null);
    },
    addAdmin() {
      this.transitionTo('main.eadmin', null);
    },
    confirmDelete(id) {
      Ember.$('.confirm-delete').css({visibility:'visible'});
      this.set('deleteAdminID',id);
    },
    editAdmin(id) {
      this.transitionTo('main.eadmin', id);
    }
}
});
