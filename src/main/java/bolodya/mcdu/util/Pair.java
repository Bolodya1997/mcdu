package bolodya.mcdu.util;

import lombok.Value;

@Value
public class Pair<F, S> {

    private F first;
    private S second;
}
