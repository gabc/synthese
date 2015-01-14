#/usr/bin/env python3

from note import scm2js

assert (scm2js("(+ 2 3)") == "2 + 3")
assert (scm2js("(if (= 2 3) (+ 2 3))") == "if(2 === 3) {2 + 3}")
assert (scm2js("(define foo (lambda (x) (* x x)))") == "var foo = function(x) {x * x}")
assert (scm2js("(define foo (lambda (x y) (if (= x y) (print x) (foo y x))))") == "var foo = function(x,y) {if(x === y) {print(x)} else { foo(y, x)}}")
