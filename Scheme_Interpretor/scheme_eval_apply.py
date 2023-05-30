import sys

from pair import *
from scheme_utils import *
from ucb import main, trace

import scheme_forms

##############
# Eval/Apply #
##############


def scheme_eval(expr, env, _=None):  # Optional third argument is ignored
    """Evaluate Scheme expression EXPR in Frame ENV.

    >>> expr = read_line('(+ 2 2)')
    >>> expr
    Pair('+', Pair(2, Pair(2, nil)))
    >>> scheme_eval(expr, create_global_frame())
    4
    """
    # Evaluate atoms
    if scheme_symbolp(expr):
        return env.lookup(expr)
    elif self_evaluating(expr):
        return expr

    # All non-atomic expressions are lists (combinations)
    if not scheme_listp(expr):
        raise SchemeError('malformed list: {0}'.format(repl_str(expr)))
    first, rest = expr.first, expr.rest #breaking up the Pair
    if scheme_symbolp(first) and first in scheme_forms.SPECIAL_FORMS: #if expr is a built in function 
        return scheme_forms.SPECIAL_FORMS[first](rest, env)
    else: #if first is not in special forms dictionary
        # BEGIN PROBLEM 3
        expr_lookup = scheme_eval(first, env)
        if isinstance(expr_lookup, Procedure):
            mapped_operands = rest.map(lambda operand: scheme_eval(operand, env))
            return scheme_apply(expr_lookup, mapped_operands, env)
        raise SchemeError('malformed list: {0}'.format(repl_str(expr)))
        
        "*** YOUR CODE HERE ***"
        # END PROBLEM 3


def scheme_apply(procedure, args, env):
    """Apply Scheme PROCEDURE to argument values ARGS (a Scheme list) in
    Frame ENV, the current environment."""
    validate_procedure(procedure)
    if not isinstance(env, Frame):
       assert False, "Not a Frame: {}".format(env)
    if isinstance(procedure, BuiltinProcedure):
        # BEGIN PROBLEM 2
        args_lst=[]
        def helper(args):
            if args is nil:
                return args_lst
            else:
                args_lst.append(args.first)
                return helper(args.rest)
        helper(args)
        if procedure.need_env:
            args_lst.append(env)
        "*** YOUR CODE HERE ***"
        # END PROBLEM 2
        try:
            # BEGIN PROBLEM 2
            return procedure.py_func(*args_lst)
            # END PROBLEM 2
        except TypeError as err:
            raise SchemeError('incorrect number of arguments: {0}'.format(procedure))
    elif isinstance(procedure, LambdaProcedure):
        # BEGIN PROBLEM 9
        new_frame = procedure.env.make_child_frame(procedure.formals, args)
        return eval_all(procedure.body, new_frame)
        "*** YOUR CODE HERE ***"
        # END PROBLEM 9
    elif isinstance(procedure, MuProcedure):
        # BEGIN PROBLEM 11
        "*** YOUR CODE HERE ***"
        #make new environment where the parent frame is the environment where it was called 
        new_frame = env.make_child_frame(procedure.formals, args)
        return eval_all(procedure.body, new_frame)
        #do evalapply using the new environment 
        # END PROBLEM 11
    else:
        assert False, "Unexpected procedure: {}".format(procedure)


def eval_all(expressions, env):
    """Evaluate each expression in the Scheme list EXPRESSIONS in
    Frame ENV (the current environment) and return the value of the last.

    >>> eval_all(read_line("(1)"), create_global_frame())
    1
    >>> eval_all(read_line("(1 2)"), create_global_frame())
    2
    >>> x = eval_all(read_line("((print 1) 2)"), create_global_frame())
    1
    >>> x
    2
    >>> eval_all(read_line("((define x 2) x)"), create_global_frame())
    2
    """
    # BEGIN PROBLEM 6
    if len(expressions) == 0:
        return None 

    while expressions.rest is not nil:
        scheme_eval(expressions.first, env)
        expressions = expressions.rest

    return scheme_eval(expressions.first, env, True)  #put True here 


    '''
    if len(expressions) == 0:
        return None 
    else:
        evaled_lst = []
        first_eval = scheme_eval(expressions.first, env)
        evaled_lst.append(first_eval)
        if expressions.rest is not nil:
            return eval_all(expressions.rest, env)
        else:
            return evaled_lst[-1]
    '''
    

    

    #return scheme_eval(expressions.first, env, True)  # replace this with lines of your own code
    # END PROBLEM 6


##################
# Tail Recursion #
##################

class Unevaluated:
    """An expression and an environment in which it is to be evaluated."""

    def __init__(self, expr, env):
        """Expression EXPR to be evaluated in Frame ENV."""
        self.expr = expr
        self.env = env


def complete_apply(procedure, args, env):
    """Apply procedure to args in env; ensure the result is not an Unevaluated."""
    validate_procedure(procedure)
    val = scheme_apply(procedure, args, env)
    if isinstance(val, Unevaluated):
        return scheme_eval(val.expr, val.env)
    else:
        return val


def optimize_tail_calls(unoptimized_scheme_eval):
    """Return a properly tail recursive version of an eval function."""
    def optimized_eval(expr, env, tail=False):
        """Evaluate Scheme expression EXPR in Frame ENV. If TAIL,
        return an Unevaluated containing an expression for further evaluation.
        """
        if tail and not scheme_symbolp(expr) and not self_evaluating(expr):
            return Unevaluated(expr, env)

        result = Unevaluated(expr, env)
        #result.expr 
        #result.env 
        # BEGIN PROBLEM EC
        "*** YOUR CODE HERE ***"
        #isinstance(variable, type) 
        #isinstance(result, Unevaluated) either returns True or False 
        #keep on evaluating the result until it is not an Unevaluated instance 
        while isinstance(result, Unevaluated): 
            #keep on evaluating it (whatever it is)
            result = unoptimized_scheme_eval(result.expr, result.env)
            #some function here that is the old scheme_eval(,) related to the result's expr and env
        return result 

        # END PROBLEM EC
    return optimized_eval


################################################################
# Uncomment the following line to apply tail call optimization #
################################################################
scheme_eval = optimize_tail_calls(scheme_eval) #uncommented this line to work on extra cred 