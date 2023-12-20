package com.feefo.normaliser;

import java.util.Optional;

public interface Normaliser<T> {
     /**
      * Contract for a normaliser which possibly returns a normalised type of type T.
      *
      * Why possibly?
      *
      * 1. Because depending on the scoring of the normalisation, it might not be appropriate to get a result back, for example:
      *
      * a. The normaliser might not give a score greater than zero.
      * b. The normaliser might score multiple options wit the same score - in this case, it should not show bias to any particular option.
      *
      * 2. It programs into the interface that the caller has to handle the case that they may not get a normalised value back.
      *
      * 3. Returning null is ambiguous - this could be an internal error i.e a NPE result, and you can't tell this apart from a valid
      *  input which the normaliser is choosing not to return a result back from.
      * @param t raw input
      * @return normalised output
      */
     Optional<T> normalise(T t);
}
