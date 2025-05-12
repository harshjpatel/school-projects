import Ember from 'ember';

export default Ember.Route.extend({
  deleteClassID: null,

  model() {
    //route retrives all classess from api and passes them to setupController
    return this.get('store').findAll('class');
  },
  setupController: function(controller, model) {
    //this takes the route's model and allows the template to gain access
      this._super(controller, model);
      controller.set('model', model);
      controller.set('isClass', true);
  },
  actions: {
    deleteModel() {
      let $this = this;
      let id = this.get('deleteClassID');
      console.log("Deleting Class with ID "+id);
      this.get('store').findRecord('class',id, {backgroundReload: false}).then(function(clasz){
        clasz.destroyRecord().then(function(){
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
      this.set('deleteClassID',null);
    },
    addClass() {
      this.transitionTo('main.eclass', null);
    },
    confirmDelete(id) {
      console.log("deleted id is "+id);
      Ember.$('.confirm-delete').css({visibility:'visible'});
      this.set('deleteClassID',id);
    },
    editClass(id) {
      this.transitionTo('main.eclass', id);
    }
}
});
