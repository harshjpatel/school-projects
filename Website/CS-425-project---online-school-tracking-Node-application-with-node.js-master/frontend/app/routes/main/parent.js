import Ember from 'ember';

export default Ember.Route.extend({
  deleteParentID: null,

  model() {
    //route retrives all parentss from api and passes them to setupController
    return this.get('store').findAll('parent');
  },
  setupController: function(controller, model) {
    //this takes the route's model and allows the template to gain access
      this._super(controller, model);
      controller.set('model', model);
      controller.set('isParent', true);
  },
  actions: {
    deleteModel() {
      let $this = this;
      let id = this.get('deleteParentID');
      console.log("Deleting Parent with ID "+id);
      this.get('store').findRecord('parent',id, {backgroundReload: false}).then(function(parent){
        parent.destroyRecord().then(function(){
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
      this.set('deleteParentID',null);
    },
    confirmDelete(id) {
      Ember.$('.confirm-delete').css({visibility:'visible'});
      this.set('deleteparentID',id);
    },
}
});
