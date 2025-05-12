import DS from 'ember-data';

export default DS.Model.extend({
  mother_name: DS.attr('string'),
  father_name: DS.attr('string'),
  parent_phone: DS.attr('number'),
  students: DS.hasMany('student',{ async: true, inverse: null })
});
