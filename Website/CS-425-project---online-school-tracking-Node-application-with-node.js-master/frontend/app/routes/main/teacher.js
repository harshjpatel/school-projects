import Ember from 'ember';

export default Ember.Route.extend({
  deleteTeacherID: null,

  model() {
    //route retrives all teachers from api and passes them to setupController
    return this.get('store').findAll('teacher');
  },
  setupController: function(controller, model) {
    //this takes the route's model and allows the template to gain access
      this._super(controller, model);
      controller.set('model', model);
      controller.set('isTeacher', true);
  },
  actions: {
    deleteModel() {
      let $this = this;
      let id = this.get('deleteTeacherID');
      console.log("Deleting teacher with ID "+id);
      this.get('store').findRecord('teacher',id, {backgroundReload: false}).then(function(teacher){
        teacher.destroyRecord().then(function(){
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
      this.set('deleteTeacherID',null);
    },
    addTeacher() {
      console.log("add teacher clicked");
      this.transitionTo('main.eteacher',null);
    },
    confirmDelete(id) {
      Ember.$('.confirm-delete').css({visibility:'visible'});
      this.set('deleteTeacherID',id);
    },
    editTeacher(id) {
      this.transitionTo('main.eteacher',id);
    }
  }
});
