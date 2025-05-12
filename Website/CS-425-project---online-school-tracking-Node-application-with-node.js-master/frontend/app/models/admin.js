import DS from 'ember-data';
export default DS.Model.extend({
  admin_name: DS.attr('string'),
  admin_email: DS.attr('string')
});
