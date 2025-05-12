import DS from 'ember-data';

export default DS.Model.extend({
  dept_name: DS.attr('string'),
  teachers: DS.hasMany('teacher', { async: true, inverse: null }),
  students: DS.hasMany('student', { async: true, inverse: null }),
  advisors: DS.hasMany('advisor', { async: true, inverse: null }),
  classes: DS.hasMany('class', { async: true, inverse: null }),
  users: DS.hasMany('user',{ async: true, inverse: null })
});
