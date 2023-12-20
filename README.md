# Feefo Technical Task Submission - Mat Brett

## Engineering comments

### Method contract

I note that the method contract which was given in the task was:

```dtd
String normalisedTitle = n.normalise(jt);
```
Ordinarily, I would negotiate or respect the contract! But... given that this is a coding interview (!) and therefore to create a discussion point, I have changed this to:

```dtd
Optional<String> normalisedTitle = n.normalise(jt);
```

This is to accommodate the idea that the normaliser might not have a suggestion for a normalised value, or may be stuck between two or more equally weighted options.

It is also to disambiguate the semantics of `null` coming back from the normaliser. 

### Fuzzy matching strategy

My first thoughts upon reading the challenge was: "OK this is a type of fuzzy match".

My second thought was: "What libraries are out there which solve this"?

This quickly led to a description of Levenshtein distancing and a horrible looking algorithm. For the suggested time for the task, this seemed to be over the top (i.e. being able to match typos, or equivalent terms such as JavaScript and JS).

After a brief search for a suitably licensed simple fuzzy matching library with an exposed scoring system did not turn up a clear result, I decided to approach this task as an opportunity to showcase a bit of java streams knowledge and write a highly simplified tokenising algorithm which scores a point if there is a direct hit for a search word (case insensitive).

This approach is very coarse-grained and leads to some interesting shortcomings (i.e. what happens if multiple normalised values score the same - how do you deterministically select the same one for each invocation?). I anticipate that this will provide another discussion point!

