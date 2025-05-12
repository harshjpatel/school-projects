import Ember from 'ember';

export default Ember.Route.extend({
  amountTrue:[],
  amountFalse:[],

  model() {
    return Ember.RSVP.hash({
      books: this.get('store').findAll('book'),
      students: this.get('store').findAll('student'),
      amountTrue: this.get('amountTrue'),
      amountFalse: this.get('amountFalse')
    });
  },
  setupController: function(controller, model) {
    //this takes the route's model and allows the template to gain access
      this._super(controller, model);
      controller.set('model', model);
      controller.set('books',model.books);
      controller.set('students',model.students);
      // console.log("MODEDL LENGTH "+model.books.get('length'));
      var amountTrue = this.get('amountTrue');
      var amountFalse = this.get('amountFalse');
      amountTrue.clear();
      amountFalse.clear();
      model.books.forEach(function(book,index) {
        if (book.get('is_available') == true) {
          amountTrue.pushObject("");
        } else {
          amountFalse.pushObject("");
        }
        // console.log('book avail '+book.get('is_available'));
      })
      controller.set('isTable1', true);
      controller.set('isTable2', true);
      controller.set('isTable3', true);
      // controller.set('isMain4', true);
      console.log('model is '+this.modelFor(this.routeName));
  },
});
