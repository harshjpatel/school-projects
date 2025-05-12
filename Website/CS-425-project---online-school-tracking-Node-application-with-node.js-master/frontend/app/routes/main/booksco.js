import Ember from 'ember';

export default Ember.Route.extend({
  deleteBookID: null,

  model() {
    //route retrives all books from api and passes them to setupController
    return this.get('store').findAll('book');
  },
  setupController: function(controller, model) {
    //this takes the route's model and allows the template to gain access
      this._super(controller, model);
      controller.set('model', model);
      controller.set('isBookCO', true);
  },
  actions: {
    deleteModel() {
      let $this = this;
      let id = this.get('deleteBookID');
      console.log("Deleting Book with ID "+id);
      this.get('store').findRecord('book',id, {backgroundReload: false}).then(function(book){
        book.destroyRecord().then(function(){
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
      this.set('deleteBookID',null);
    },
    addBook() {
      this.transitionTo('main.ebookco', null);
    },
    confirmDelete(id) {
      Ember.$('.confirm-delete').css({visibility:'visible'});
      this.set('deleteBookID',id);
    },
    editBook(id) {
      this.transitionTo('main.ebookco', id);
    }
}
});
