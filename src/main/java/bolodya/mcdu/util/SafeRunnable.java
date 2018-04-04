package bolodya.mcdu.util;

import java.util.function.Supplier;

@FunctionalInterface
public interface SafeRunnable extends Runnable {

    static SafeRunnable build(final Supplier<Boolean> predicate,
                              final Runnable runnable) {
        return () -> {
            if (predicate.get())
                runnable.run();
        };
    }
}
