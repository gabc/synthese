from __future__ import division

Symbol = str          # A Lisp Symbol is implemented as a Python str
List   = list         # A Lisp List is implemented as a Python list
Number = (int, float) # A Lisp Number is implemented as a Python int or float

# The inline function I deal with
INLINE = ('*', '+', '-', '/', '=', '===')

def parse(program):
    "Read a Scheme expression from a string."
    return read_from_tokens(tokenize(program))

def tokenize(s):
    "Convert a string into a list of tokens."
    return s.replace('(',' ( ').replace(')',' ) ').replace('=', '===').replace('lambda','function').split()

def read_from_tokens(tokens):
    "Read an expression from a sequence of tokens."
    if len(tokens) == 0:
        raise SyntaxError('unexpected EOF while reading')
    token = tokens.pop(0)
    if '(' == token:
        L = []
        while tokens[0] != ')':
            L.append(read_from_tokens(tokens))
        tokens.pop(0) # pop off ')'
        return L
    elif ')' == token:
        raise SyntaxError('unexpected )')
    else:
        return atom(token)

def atom(token):
    "Numbers become numbers; every other token is a symbol."
    try: return int(token)
    except ValueError:
        try: return float(token)
        except ValueError:
            return Symbol(token)

def my_if(expr):
    try:
        return "if(" + print_expr(expr[1])+") {" + print_expr(expr[2])+ "} " + "else { " + print_expr(expr[3]) + "}"
    except:
        return "if(" + print_expr(expr[1])+") {" + print_expr(expr[2])+ "}"

def my_function(expr):
    return expr[0] + '(' + stringify(expr[1]) + ') {' + print_expr(expr[2]) + '}'
    
def my_define(expr):
    return 'var ' + print_expr(expr[1]) + ' = ' + print_expr(expr[2])
        
def print_expr(expr):
    if expr == []:
        return ""
    if isinstance(expr, Number):
        return str(expr)
    if isinstance(expr, str):
        return expr
    if expr[0] in INLINE:
        return print_expr(expr[1]) + " " + expr[0] + " " + print_expr(expr[2])
    try:
        return globals()['my_'+expr[0]](expr)
    except:
        pass
    s = "" + expr[0] + '('
    foo = False
    for i in expr[1:]:
        foo = True
        s += print_expr(i) + ', '
    if foo:
        a = s[:-2] + ")"
    else:
        a = s + ')'
    return a

def stringify(lst):
    s = ""
    foo = False
    for i in lst:
        foo = True
        s += i + ","
    if foo:
        s = s[:-1]
    return s

def add_if_return(ast):
    ast[2] = ['return', ast[2]]
    try:
        ast[3] = ['return', ast[3]]
    except:
        pass
    return ast

def add_fn_return(ast):
    if ast[2][0] == 'if':
        ast[2] = add_if_return(ast[2])
    else:
        ast[2] = ['return', ast[2]]
    return ast

def add_return(ast):
    if ast[0] == 'define' and ast[2][0] == 'function':
        ast[2] = add_fn_return(ast[2])
    return ast
    
def scm2js(string):
    ast = parse(string)
    # print(ast)
    ast = add_return(ast)
    return print_expr(ast)
