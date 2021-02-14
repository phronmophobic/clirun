# clirun

Run any function from clojure's cli.

## Usage

Add an alias to your `deps.edn`:

```clojure
  :run
  {:extra-deps {com.phronemophobic/clirun {:mvn/version "1.0.0"}}
   :main-opts ["-m" "com.phronemophobic.clirun"]}
```

Run any clojure function from the command line. Arguments are parsed as edn.


```bash
$ clj -M:run my.ns/foo arg1 arg2 
```

Examples:


```bash
# Write to a file using clojure.core/split
$ clj -M:run clojure.core/spit '"foo.txt"' '[1 2 3]'
$ cat foo.txt
[1 2 3]

# prn the return value by
# adding -p as the first argument
$ clj -M:run -p clojure.core/+ 1 2 3 4
10

# pretty print the return value by
# adding -pp as the first value
$ clj -M:run -pp  clojure.core/macroexpand-1 '(time (+ 1 2 3))'
(clojure.core/let
 [start__6153__auto__
  (. java.lang.System (clojure.core/nanoTime))
  ret__6154__auto__
  (+ 1 2 3)]
 (clojure.core/prn
  (clojure.core/str
   "Elapsed time: "
   (clojure.core//
    (clojure.core/double
     (clojure.core/-
      (. java.lang.System (clojure.core/nanoTime))
      start__6153__auto__))
    1000000.0)
   " msecs"))
 ret__6154__auto__)
 
# mix and match
$ clj -M -e "(range 50)" | xargs -0 clj -M:run -pp clojure.core/partition 5
((0 1 2 3 4)
 (5 6 7 8 9)
 (10 11 12 13 14)
 (15 16 17 18 19)
 (20 21 22 23 24)
 (25 26 27 28 29)
 (30 31 32 33 34)
 (35 36 37 38 39)
 (40 41 42 43 44)
 (45 46 47 48 49))

```

## License

Copyright © 2021 Adrian

Distributed under the Eclipse Public License version 1.0.
