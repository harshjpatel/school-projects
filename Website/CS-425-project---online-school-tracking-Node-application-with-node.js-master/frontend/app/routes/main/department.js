import Ember from 'ember';

export default Ember.Route.extend({
  deleteDepartmentID: null,

  model() {
    //route retrives all departments from api and passes them to setupController
    return this.get('store').findAll('department');
  },
  setupController: function(controller, model) {
    //this takes the route's model and allows the template to gain access
      this._super(controller, model);
      controller.set('model', model);
      controller.set('isDepartment', true);
  },
  actions: {
    deleteModel() {
      let $this = this;
      let id = this.get('deleteDepartmentID');
      console.log("Deleting Department with ID "+id);
      this.get('store').findRecord('department',id, {backgroundReload: false}).then(function(department){
        department.destroyRecord().then(function(){
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
      this.set('deleteDepartmentID',null);
    },
    addDepartment() {
      this.transitionTo('main.edepartment', null);
    },
    confirmDelete(id) {
      Ember.$('.confirm-delete').css({visibility:'visible'});
      this.set('deleteDepartmentID',id);
    },
    editDepartment(id) {
      this.transitionTo('main.edepartment', id);
    }

}
});
