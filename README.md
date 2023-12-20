# Feefo Technical Task Submission - Mat Brett

## Comments

### Method contract

I note that the method contract which was given in the task was:

```dtd
String normalisedTitle = n.normalise(jt);
```
Ordinarily, I would respect the contract, but given that this is a coding interview (!) and to create a talking point, I have changed this to:

```dtd
Optional<String> normalisedTitle = n.normalise(jt);
```

This is to accommodate the idea that the normaliser might not have a suggestion for a normalised value, or may be stuck between two or more equallty weighted options.

It is also to disambiguate the semantics of `null` coming back from the normaliser. 
