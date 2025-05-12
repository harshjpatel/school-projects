import DS from 'ember-data';

export default DS.Model.extend({
  student_name: DS.attr('string'),
  student_email: DS.attr('string'),
  student_address: DS.attr('string'),
  student_phone: DS.attr('string'),
  student_ID: DS.attr('string'),
  parent: DS.belongsTo('parent', { async: true, inverse: null }),
  advisor: DS.belongsTo('advisor',{ async: true, inverse: null }),
  department: DS.belongsTo('department',{ async: true, inverse: null }),
  books: DS.hasMany('book', { async: true, inverse: null }),
  classes: DS.hasMany('class', { async: true, inverse: null })
});
