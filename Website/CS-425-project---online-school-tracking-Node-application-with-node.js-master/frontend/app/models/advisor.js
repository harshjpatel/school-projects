import DS from 'ember-data';

export default DS.Model.extend({
  advisor_name: DS.attr('string'),
  advisor_email: DS.attr('string'),
  department: DS.belongsTo('department', { async: true, inverse: null }),
  students: DS.hasMany('student',{ async: true, inverse: null })
});
