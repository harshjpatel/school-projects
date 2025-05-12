import Ember from 'ember';

const GeneralEditComponent = Ember.Component.extend({
  store: Ember.inject.service(),
  r1: null,
  r2: null,
  r3: null,
  r4: null,
  r5: null,
  r6: null,
  r7: null,
  r8: null,
  r9: null,
  r10: null,
  r11: null,
  r12: null,
  r13: null,
  row1: Ember.computed('params.[]', function(){
    try {
      let v = this.get('params')[0];
      return v;
    } catch (e) {
      return null;
    }
  }),
  row2: Ember.computed('params.[]', function(){
    try {
      let v = this.get('params')[1];
      return v;
    } catch (e) {
      return null;
    }
  }),
  row3: Ember.computed('params.[]', function(){
    try {
      let v = this.get('params')[2];
      return v;
    } catch (e) {
      return null;
    }
  }),
  row4: Ember.computed('params.[]', function(){
    try {
      let v = this.get('params')[3];
      return v;
    } catch (e) {
      return null;
    }
  }),
  row5: Ember.computed('params.[]', function(){
    try {
      let v = this.get('params')[4];
      return v;
    } catch (e) {
      return null;
    }
  }),
  row6: Ember.computed('params.[]', function(){
    try {
      let v = this.get('params')[5];
      return v;
    } catch (e) {
      return null;
    }
  }),
  row7: Ember.computed('params.[]', function(){
    try {
      let v = this.get('params')[6];
      return v;
    } catch (e) {
      return null;
    }
  }),
  row8: Ember.computed('params.[]', function(){
    try {
      let v = this.get('params')[7];
      return v;
    } catch (e) {
      return null;
    }
  }),
  row9: Ember.computed('params.[]', function(){
    try {
      let v = this.get('params')[8];
      return v;
    } catch (e) {
      return null;
    }
  }),
  row10: Ember.computed('params.[]', function(){
    try {
      let v = this.get('params')[9];
      return v;
    } catch (e) {
      return null;
    }
  }),
  row11: Ember.computed('params.[]', function(){
    try {
      let v = this.get('params')[10];
      return v;
    } catch (e) {
      return null;
    }
  }),
  row12: Ember.computed('params.[]', function(){
    try {
      let v = this.get('params')[11];
      return v;
    } catch (e) {
      return null;
    }
  }),
  row13: Ember.computed('params.[]', function(){
    try {
      let v = this.get('params')[12];
      return v;
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
  isBookCO: false,
  isBookFound: false,
  bookTitle: Ember.computed(function() {
    return "";
  }),
  bookISBN: Ember.computed(function() {
    return "";
  }),
  bookAuthors: Ember.computed(function() {
    return "";
  }),
  classArray: [], //stores strings of the classes,
  bookArray: [], //stores strings of books
  studentArray: [], //stores strings of students

  init() {
    this._super(...arguments);
    //add original models to classArray
    let classArray = this.get('classArray');
    let bookArray = this.get('bookArray');
    let studentArray = this.get('studentArray');
    let model = this.get('model');
    let store = this.get('store');

    console.log("init classArraySize "+classArray.get('length'));
    if (model) {
      if (this.get('isTeacher') || this.get('isStudent')) {
        model.get('classes').forEach(function(clas,index) {
          store.findRecord('class',clas.get('id')).then(function(cla) {
            //check if already in the array
            let claName = classArray.objectAt(index);
            if (claName != cla.get('class_name')) {
              //no duplicates
              classArray.pushObject(cla.get('class_name'));
              console.log("ClassArrayModel "+classArray.get('length'));
            }
          });
        });
        model.get('books').forEach(function(book,index) {
          store.findRecord('book',book.get('id')).then(function(boo) {
            //check if already in the array
            let bookName = bookArray.objectAt(index);
            if (bookName != boo.get('book_name')) {
              //no duplicates
              bookArray.pushObject(boo.get('book_name'));
            }
          });
        });
      }
      else if (this.get('isClass')) {
        model.get('students').forEach(function(student,index) {
          store.findRecord('student',student.get('id')).then(function(stu) {
            //check if already in the array
            let stuName = studentArray.objectAt(index);
            if (stuName != stu.get('student_name')) {
              //no duplicates
              studentArray.pushObject(stu.get('student_name'));
            }
          });
        });
      }
    } else {
      classArray.clear();
      bookArray.clear();
      studentArray.clear();
      console.log('no model given');
      classArray.pushObject();
      bookArray.pushObject();
      studentArray.pushObject();
    }
  },

  actions: {
    save() {
      //needs to check if the department, classes, email, and teacher already exist..
      if (this.get('isTeacher')) {
        let classArray = this.get('classArray');
        //remove empty classes
        classArray.forEach(function(cla,index) {
          console.log('classSize1 '+classArray.get('length'));
          if (cla == "") {
            classArray.removeAt(index);
          }
        });
        let model = this.get('model');
        if (model != null) {
          let name = model.get('teacher_name');
          let dept = model.get('department').get('dept_name');
          let email = model.get('teacher_email');
          this.sendAction('save',name,email,classArray,dept);
        } else {
          let r1 = this.get('r1');
          let r2 = this.get('r2');
          let r5 = this.get('r5');
         this.sendAction('save',r1,r2,classArray,r5);
        }
      }
      else if (this.get('isStudent')) {
        let classArray = this.get('classArray');
        let bookArray = this.get('bookArray');
        //remove empty classes
        classArray.forEach(function(cla,index) {
          if (cla == "") {
            classArray.removeAt(index);
          }
        })
        //remove empty books
        bookArray.forEach(function(book,index) {
          if (book == "") {
            bookArray.removeAt(index);
          }
        });
        let model = this.get('model');
        if (model != null) {
          let sid = model.get('student_ID');
          let name = model.get('student_name');
          let email = model.get('student_email');
          let phone = model.get('student_phone');
          let address = model.get('student_address');
          let mother = model.get('parent').get('mother_name');
          let father = model.get('parent').get('father_name');
          let advisor = model.get('advisor').get('advisor_name');
          let dept = model.get('department').get('dept_name');
          this.sendAction('save',sid,name,email,phone,address,mother,father,advisor,dept,bookArray,classArray);
        } else {
          let r1 = this.get('r1');
          let r2 = this.get('r2');
          let r3 = this.get('r3');
          let r4 = this.get('r4');
          let r5 = this.get('r5');
          let r6 = this.get('r6');
          let r7 = this.get('r7');
          let r8 = this.get('r8');
          let r9 = this.get('r9');
          this.sendAction('save',r1,r2,r3,r4,r5,r6,r7,r8,r9,bookArray,classArray);
        }
      }
      else if (this.get('isClass')) {
        let studentArray = this.get('studentArray')
        studentArray.forEach(function(student,index) {
          if (student == "") {
            studentArray.removeAt(index);
          }
        });
        let model = this.get('model');
        if (model != null) {
          let name = model.get('class_name');
          let dept = model.get('department').get('dept_name');
          let teacher = model.get('teacher').get("teacher_name");
          this.sendAction('save',name,dept,teacher,studentArray);
        } else {
          let r1 = this.get('r1');
          let r2 = this.get('r2');
          let r3 = this.get('r3');
          this.sendAction('save',r1,r2,r3,studentArray);
        }
      } else if (this.get('isAdmin')) {
        let model = this.get('model');
        if (model != null) {
          let name = model.get('admin_name');
          let email = model.get('admin_email');
          this.sendAction('save',name,email);
        } else {
          let r1 = this.get('r1');
          let r2 = this.get('r2');
          this.sendAction('save',r1,r2);
        }

      } else if (this.get('isDepartment')) {
        let model = this.get('model');
        if (model != null) {
          let name = model.get('dept_name');
          this.sendAction('save',name);
        } else {
          let r1 = this.get('r1');
          this.sendAction('save',r1);
        }
      } else if (this.get('isAdvisor')) {
        let model = this.get('model');
        if (model != null) {
          let name = model.get('advisor_name');
          let email = model.get('advisor_email');
          let dept = model.get('department').get('dept_name');
          this.sendAction('save',name,email,dept);
        } else {
          let r1 = this.get('r1');
          let r2 = this.get('r2');
          let r3 = this.get('r3');
          this.sendAction('save',r1,r2,r3);
        }
      } else if (this.get('isBook') || this.get('isBookCO')) {
        let model = this.get('model');
        if (model != null) {
          let title = model.get('book_name');
          let isbn = model.get('isbn');
          let author = model.get('author');
          let price = model.get('price')
          let checkoutDate = model.get('checkout_date');
          let dueDate = model.get('due_date');
          this.sendAction('save',title,isbn,author,price,checkoutDate,dueDate);
        } else {
          let r1 = this.get('bookTitle');
          let r2 = this.get('bookISBN');
          let r3 = this.get('bookAuthors');
          let r4 = this.get('r4');
          let r5 = this.get('r5');
          let r6 = this.get('r6');
          this.sendAction('save',r1,r2,r3,r4,r5,r6);
        }
      } else if (this.get('isUser')) {
        let model = this.get('model');
        if (model != null) {
          let name = model.get('user_name');
          let email = model.get('user_email');
          let dept = model.get('department').get('dept_name');
          this.sendAction('save',name,email,dept);
        } else {
          let r1 = this.get('r1');
          let r2 = this.get('r2');
          let r3 = this.get('r3');
          this.sendAction('save',r1,r2,r3);
        }
      }
      else {
        let r1 = this.get('r1');
        let r2 = this.get('r2');
        let r3 = this.get('r3');
        let r4 = this.get('r4');
        let r5 = this.get('r5');
        let r6 = this.get('r6');
        let r7 = this.get('r7');
        let r8 = this.get('r8');
        this.sendAction('save',r1,r2,r3,r4,r5,r6,r7,r8);
      }
    },
    findBook() {
      let r1 = this.get('bookTitle');
      let r2 = this.get('bookISBN');
      let r3 = this.get('bookAuthors');
      this.sendAction('findBook',r1,r2,r3);
    },
    populateFields(title,isbn,authors) {
      this.set('bookTitle',title);
      this.set('bookISBN',isbn);
      this.set('bookAuthors',authors);
    },
    addClassRow() {
      let classArray = this.get('classArray');
      classArray.pushObject("");
    },
    deleteClassRow(index) {
      let classArray = this.get('classArray');
      classArray.removeAt(index);
    },
    updateClassRow(index) {
       this.get('classArray')[index] = event.target.value;
       this.notifyPropertyChange('classArray');
    },
    addBookRow() {
      let bookArray = this.get('bookArray');
      bookArray.pushObject("");
    },
    deleteBookRow(index) {
      let bookArray = this.get('bookArray');
      bookArray.removeAt(index);
    },
    updateBookRow(index) {
       this.get('bookArray')[index] = event.target.value;
       this.notifyPropertyChange('bookArray');
    },
    addStudentRow() {
      let studentArray = this.get('studentArray');
      studentArray.pushObject("");
    },
    deleteStudentRow(index) {
      let studentArray = this.get('studentArray');
      studentArray.removeAt(index);
    },
    updateStudentRow(index) {
       this.get('studentArray')[index] = event.target.value;
       this.notifyPropertyChange('studentArray');
    }
  }
});

GeneralEditComponent.reopenClass({
  positionalParams: 'params'
});

export default GeneralEditComponent;
