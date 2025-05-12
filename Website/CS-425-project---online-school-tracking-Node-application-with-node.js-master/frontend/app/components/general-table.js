import Ember from 'ember';

const GeneralTableComponent = Ember.Component.extend({
  column1: Ember.computed('params.[]', function(){
    try {
      let v = this.get('params')[0];
      return v;
    } catch (e) {
      return null;
    }
  }),
  column2: Ember.computed('params.[]', function(){
    try {
      let v = this.get('params')[1];
      return v;
    } catch (e) {
      return null;
    }
  }),
  column3: Ember.computed('params.[]', function(){
    try {
      let v = this.get('params')[2];
      return v;
    } catch (e) {
      return null;
    }
  }),
  column4: Ember.computed('params.[]', function(){
    try {
      let v = this.get('params')[3];
      return v;
    } catch (e) {
      return null;
    }
  }),
  column5: Ember.computed('params.[]', function(){
    try {
      let v = this.get('params')[4];
      return v;
    } catch (e) {
      return null;
    }
  }),
  column6: Ember.computed('params.[]', function(){
    try {
      let v = this.get('params')[5];
      return v;
    } catch (e) {
      return null;
    }
  }),
  column7: Ember.computed('params.[]', function(){
    try {
      let v = this.get('params')[6];
      return v;
    } catch (e) {
      return null;
    }
  }),
  column8: Ember.computed('params.[]', function(){
    try {
      let v = this.get('params')[7];
      return v;
    } catch (e) {
      return null;
    }
  }),
  model: Ember.computed('param.[]', function() {
    try {
      let model = this.get('params')[8];
      return model;
    } catch (e) {
      return null;
    }
  }),

  isAdmin: false,
  isAdvisor: false,
  isBook: false,
  isClass: false,
  isDepartment: false,
  isParent: false,
  isStudent: false,
  isTeacher: false,
  isUser: false,

  actions: {
    confirmDelete(id) {
      this.sendAction('confirmDelete',id);
    },
    editAdmin(id) {
      this.sendAction('editAdmin',id);
    },
    editAdvisor(id) {
      this.sendAction('editAdvisor',id);
    },
    editBook(id) {
      this.sendAction('editBook',id);
    },
    editClass(id) {
      this.sendAction('editClass',id);
    },
    editDepartment(id) {
      this.sendAction('editDepartment',id);
    },
    editParent(id) {
      this.sendAction('editParent',id);
    },
    editStudent(id) {
      this.sendAction('editStudent',id);
    },
    editTeacher(id) {
      this.sendAction('editTeacher',id);
    },
    editUser(id) {
      this.sendAction('editUser',id);
    }
  }
});

GeneralTableComponent.reopenClass({
  positionalParams: 'params'
});

export default GeneralTableComponent;
