import DS from 'ember-data';

export default DS.Model.extend({
  teacher_name: DS.attr('string'),
  teacher_email: DS.attr('string'),
  classes: DS.hasMany('class', { async: true, inverse: null }),
  department: DS.belongsTo('department', { async: true, inverse: null }),
  books: DS.hasMany('book',{ async: true, inverse: null })
});
