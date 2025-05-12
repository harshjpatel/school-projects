import DS from 'ember-data';
import Ember from 'ember';
import moment from 'moment';
export default DS.Model.extend({
  book_name: DS.attr('string'),
  author: DS.attr('string'),
  isbn: DS.attr('string'),
  is_available: Ember.computed('checkout_date','due_date',function() {
    if ((this.get('checkout_date') != null) || (this.get('due_date') != null)) {
      return false;
    } else {
      return true;
    }
  }),
  is_due: Ember.computed('price','due_date',function() {
    var todayDate = new Date().getTime();
    var dueDate = Date.parse(this.get('due_date'));
    if (todayDate > dueDate) {
      return true;
    } else {
      return false;
    }
  }),
  checkout_date: DS.attr('string'),
  due_date: DS.attr('string'),
  price: DS.attr('number'),
  student: DS.belongsTo('student',{ async: true, inverse: null }),
  teacher: DS.belongsTo('teacher',{ async: true, inverse: null })
});
