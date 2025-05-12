import FirebaseSerializer from 'emberfire/serializers/firebase';
import Ember from 'ember';

export default FirebaseSerializer.extend({
  firebase: Ember.inject.service()
});
