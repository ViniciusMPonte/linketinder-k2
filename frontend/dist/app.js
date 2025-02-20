(function(){function r(e,n,t){function o(i,f){if(!n[i]){if(!e[i]){var c="function"==typeof require&&require;if(!f&&c)return c(i,!0);if(u)return u(i,!0);var a=new Error("Cannot find module '"+i+"'");throw a.code="MODULE_NOT_FOUND",a}var p=n[i]={exports:{}};e[i][0].call(p.exports,function(r){var n=e[i][1][r];return o(n||r)},p,p.exports,r,e,n,t)}return n[i].exports}for(var u="function"==typeof require&&require,i=0;i<t.length;i++)o(t[i]);return o}return r})()({1:[function(require,module,exports){
"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const MyClass_1 = require("./test/MyClass");
const test = 'Hello World with TypeScript';
console.log(test);
const myClassInstance = new MyClass_1.MyClass();
myClassInstance.printMessage();

},{"./test/MyClass":2}],2:[function(require,module,exports){
"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.MyClass = void 0;
class MyClass {
    printMessage() {
        console.log('Hello from MyClass');
    }
}
exports.MyClass = MyClass;

},{}]},{},[1])
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIm5vZGVfbW9kdWxlcy9icm93c2VyLXBhY2svX3ByZWx1ZGUuanMiLCJmcm9udGVuZC9zcmMvbWFpbi50cyIsImZyb250ZW5kL3NyYy90ZXN0L015Q2xhc3MudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7OztBQ0FBLDRDQUF1QztBQUV2QyxNQUFNLElBQUksR0FBVyw2QkFBNkIsQ0FBQztBQUNuRCxPQUFPLENBQUMsR0FBRyxDQUFDLElBQUksQ0FBQyxDQUFBO0FBRWpCLE1BQU0sZUFBZSxHQUFHLElBQUksaUJBQU8sRUFBRSxDQUFDO0FBQ3RDLGVBQWUsQ0FBQyxZQUFZLEVBQUUsQ0FBQzs7Ozs7O0FDTi9CLE1BQWEsT0FBTztJQUNoQixZQUFZO1FBQ1IsT0FBTyxDQUFDLEdBQUcsQ0FBQyxvQkFBb0IsQ0FBQyxDQUFDO0lBQ3RDLENBQUM7Q0FDSjtBQUpELDBCQUlDIiwiZmlsZSI6ImdlbmVyYXRlZC5qcyIsInNvdXJjZVJvb3QiOiIiLCJzb3VyY2VzQ29udGVudCI6WyIoZnVuY3Rpb24oKXtmdW5jdGlvbiByKGUsbix0KXtmdW5jdGlvbiBvKGksZil7aWYoIW5baV0pe2lmKCFlW2ldKXt2YXIgYz1cImZ1bmN0aW9uXCI9PXR5cGVvZiByZXF1aXJlJiZyZXF1aXJlO2lmKCFmJiZjKXJldHVybiBjKGksITApO2lmKHUpcmV0dXJuIHUoaSwhMCk7dmFyIGE9bmV3IEVycm9yKFwiQ2Fubm90IGZpbmQgbW9kdWxlICdcIitpK1wiJ1wiKTt0aHJvdyBhLmNvZGU9XCJNT0RVTEVfTk9UX0ZPVU5EXCIsYX12YXIgcD1uW2ldPXtleHBvcnRzOnt9fTtlW2ldWzBdLmNhbGwocC5leHBvcnRzLGZ1bmN0aW9uKHIpe3ZhciBuPWVbaV1bMV1bcl07cmV0dXJuIG8obnx8cil9LHAscC5leHBvcnRzLHIsZSxuLHQpfXJldHVybiBuW2ldLmV4cG9ydHN9Zm9yKHZhciB1PVwiZnVuY3Rpb25cIj09dHlwZW9mIHJlcXVpcmUmJnJlcXVpcmUsaT0wO2k8dC5sZW5ndGg7aSsrKW8odFtpXSk7cmV0dXJuIG99cmV0dXJuIHJ9KSgpIiwiaW1wb3J0IHtNeUNsYXNzfSBmcm9tICcuL3Rlc3QvTXlDbGFzcyc7XG5cbmNvbnN0IHRlc3Q6IHN0cmluZyA9ICdIZWxsbyBXb3JsZCB3aXRoIFR5cGVTY3JpcHQnO1xuY29uc29sZS5sb2codGVzdClcblxuY29uc3QgbXlDbGFzc0luc3RhbmNlID0gbmV3IE15Q2xhc3MoKTtcbm15Q2xhc3NJbnN0YW5jZS5wcmludE1lc3NhZ2UoKTtcblxuIiwiZXhwb3J0IGNsYXNzIE15Q2xhc3Mge1xuICAgIHByaW50TWVzc2FnZSgpOiB2b2lkIHtcbiAgICAgICAgY29uc29sZS5sb2coJ0hlbGxvIGZyb20gTXlDbGFzcycpO1xuICAgIH1cbn0iXX0=
