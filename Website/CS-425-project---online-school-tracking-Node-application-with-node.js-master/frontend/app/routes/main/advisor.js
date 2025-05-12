import Ember from 'ember';

export default Ember.Route.extend({
  deleteAdvisorID: null,

  model() {
    //route retrives all advisors from api and passes them to setupController
    return this.get('store').findAll('advisor');
  },
  setupController: function(controller, model) {
    //this takes the route's model and allows the template to gain access
      this._super(controller, model);
      controller.set('model', model);
      controller.set('isAdvisor', true);
  },
  actions: {
    deleteModel() {
      let $this = this;
      let id = this.get('deleteAdvisorID');
      console.log("Deleting Advisor with ID "+id);
      this.get('store').findRecord('advisor',id, {backgroundReload: false}).then(function(advisor){
        advisor.destroyRecord().then(function(){
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
      this.set('deleteAdvisorID',null);
    },
    addAdvisor() {
      this.transitionTo('main.eadvisor', null);
    },
    confirmDelete(id) {
      Ember.$('.confirm-delete').css({visibility:'visible'});
      this.set('deleteAdvisorID',id);
    },
    editAdvisor(id) {
      this.transitionTo('main.eadvisor', id);
    }
}
});
