#/usr/bin/env python3

from note import scm2js

assert (scm2js("(define foo (lambda (x) (+ x x)))") == 'var foo = function(x) {return(x + x)}')
assert (scm2js("(define foo (lambda (x) (if (= x 2) (+ x x))))") == 'var foo = function(x) {if(x === 2) {return(x + x)}}')


