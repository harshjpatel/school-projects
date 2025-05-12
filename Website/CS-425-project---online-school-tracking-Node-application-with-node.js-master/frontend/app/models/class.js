import DS from 'ember-data';

export default DS.Model.extend({
  class_name: DS.attr('string'),
  department: DS.belongsTo('department', { async: true, inverse: null }),
  teacher: DS.belongsTo('teacher', { async: true, inverse: null }),
  students: DS.hasMany('student',{ async: true, inverse: null })
});
