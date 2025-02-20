const {series, parallel, src, dest} = require('gulp');
const del = require('del');
const browserfily = require('browserify');
const source = require('vinyl-source-stream');
const tsify = require('tsify');

function clean(cb) {
    return del('./frontend/dist');
    //cb()
}

function copyHtml(cb) {
    cb()
}

function buildJs(cb) {
    return browserfily({
        basedir: '.',
        debug: true,
        entries: ['frontend/src/main.ts'],
    }).plugin(tsify)
        .bundle()
        .pipe(source('app.js'))
        .pipe(dest('./frontend/dist'));
    //cb()
}

exports.default = series(
    clean,
    parallel(buildJs, copyHtml)
);