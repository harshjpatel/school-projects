import Ember from 'ember';

export default Ember.Route.extend({
  deleteStudentID: null,

  model() {
    //route retrives all Students from api and passes them to setupController
    return this.get('store').findAll('student');
  },
  setupController: function(controller, model) {
    //this takes the route's model and allows the template to gain access
      this._super(controller, model);
      controller.set('model', model);
      controller.set('isStudent', true);
  },
  actions: {
    deleteModel() {
      let $this = this;
      let id = this.get('deleteStudentID');
      console.log("Deleting Student with ID "+id);
      this.get('store').findRecord('student',id, {backgroundReload: false}).then(function(student){
        student.destroyRecord().then(function(){
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
      this.set('deleteStudentID',null);
    },
    addStudent() {
      console.log("add Student clicked");
      this.transitionTo('main.estudent',null);
    },
    confirmDelete(id) {
      Ember.$('.confirm-delete').css({visibility:'visible'});
      this.set('deleteStudentID',id);
    },
    editStudent(id) {
      this.transitionTo('main.estudent',id);
    }
  }
});
