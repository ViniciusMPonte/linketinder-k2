const {series, parallel, src, dest} = require('gulp');
const del = require('del');
const browserify = require('browserify');
const source = require('vinyl-source-stream');
const tsify = require('tsify');

function clean() {
    return del(['frontend/dist'])
}

function copyHTML() {
    return src('frontend/public/**/*')
        .pipe(dest('frontend/dist'))
}

function buildJS() {
    return browserify({
        basedir: '.',
        entries: ['frontend/src/main.ts']
    })
        .plugin(tsify)
        .bundle()
        .pipe(source('app.js'))
        .pipe(dest('frontend/dist'))
}

exports.default = series(
    clean,
    parallel(buildJS, copyHTML)
)