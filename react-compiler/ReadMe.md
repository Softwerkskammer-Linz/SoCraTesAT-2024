# The react compiler

## What it is 
React Compiler is a new experimental compiler that we’ve open sourced to get early feedback from the community. It is a build-time only tool that automatically optimizes your React app. It works with plain JavaScript, and understands the Rules of React, so you don’t need to rewrite any code to use it.

## What it does
React lets you express your UI as a function of their current state (more concretely: their props, state, and context). In its current implementation, when a component’s state changes, React will re-render that component and all of its children — unless you have applied some form of manual memoization with useMemo(), useCallback(), or React.memo().

## The presentation is a short summary based on the sources of
- React Documentation: https://react.dev/learn/react-compiler
- Video from Jack Herrington https://www.youtube.com/watch?v=PYHBHK37xlE

## Demo REPLs
- Meta (Facebook, IG, …) REPL: https://playground.react.dev/#N4Igzg9grgTgxgUxALhAgHgBwjALgAgBMEAzAQygBsCSoA7OXASwjvwFkBPAQU0wAoAlPmAAdNvhgJcsNgB5CTAG4A+ABIJKlCPgDqOSoTkB6
- REPL from Jack Herrington: https://github.com/jherr/compiler-repl 
