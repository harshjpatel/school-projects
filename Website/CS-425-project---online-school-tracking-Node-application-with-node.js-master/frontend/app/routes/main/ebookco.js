import Ember from 'ember';

export default Ember.Route.extend({
  model(params) {
    return this.get('store').findRecord('book', params.id);
  },
  setupController: function(controller, model) {
    //this takes the route's model and allows the template to gain access
      this._super(controller, model);
      controller.set('model', model);
      controller.set('isBook', true);
  },
  actions: {
    async saveBook(title,isbn,author,price,checkoutDate,dueDate) {
      console.log('book '+title+" "+isbn+" "+author+" "+price);
      var currentBook;
      var store = this.get('store');
      let model = this.modelFor(this.routeName);

      if (model == null) {
        let newbook = await this.get('store').createRecord('book', {
          book_name: title,
          isbn: isbn,
          author: author,
          price: price,
          checkout_date: checkoutDate,
          due_date: dueDate
      }).save()

        this.send('goToBookList');
    } else {
      let book = await store.findRecord('book',model.id);

      book.set('book_name', title)
      book.set('isbn',isbn)
      book.set('author', author)
      book.set('price', price)
      book.set('checkout_date', checkoutDate)
      book.set('due_date', dueDate)
      await book.save()

      this.send('goToBookList');
    }
  },
    goToBookList() {
      this.transitionTo('main.bookco');
    }
  }
});
