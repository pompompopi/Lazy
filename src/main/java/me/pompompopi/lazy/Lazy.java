package me.pompompopi.lazy;

import java.util.Optional;
import java.util.function.Supplier;
import org.jetbrains.annotations.Nullable;

/**
 * An object that may or may not contain an instance of {@link T}.
 *
 * <p>The instance is initialized, using the provided {@link Supplier<T>}, when {@link
 * Lazy<T>#initialize()} is called - either by the user or by {@link Lazy<T>#get()}.
 *
 * @param <T> the type of the contained value
 */
public class Lazy<T> {

  /** The supplier instance used for initialization of the {@link Lazy<T>#value} field. */
  protected final Supplier<T> lazySupplier;

  /** The actual value. Null if not initialized yet. */
  protected @Nullable T value;

  /**
   * Creates a {@link Lazy<T>} instance with the provided supplier
   *
   * @param lazySupplier a supplier responsible for creating instances of {@link T}
   */
  protected Lazy(final Supplier<T> lazySupplier) {
    this.lazySupplier = lazySupplier;
  }

  /**
   * Creates a {@link Lazy<T>} instance.
   *
   * @param lazySupplier a supplier responsible for creating instances of {@link T} upon lazy
   *     initialization, should <strong>never</strong> throw
   * @return the created instance of {@link Lazy<T>}
   * @param <T> the type of the contained value
   */
  public static <T> Lazy<T> create(final Supplier<T> lazySupplier) {
    return new Lazy<>(lazySupplier);
  }

  /**
   * Initializes a {@link T} if necessary.
   *
   * @return if the value was initialized
   */
  public boolean initialize() {
    if (this.value != null) {
      return false;
    }

    this.value = this.lazySupplier.get();
    return true;
  }

  /**
   * Wraps the internal field containing the {@link T} around an optional, without making any calls
   * to {@link Lazy<T>#initialize()}.
   *
   * @return an empty optional if the internal field is null, else an optional containing the
   *     internal field's value
   */
  public Optional<T> getWithoutInitialization() {
    return Optional.ofNullable(this.value);
  }

  /**
   * Gets the contained {@link T} instance, initializing it if necessary.
   *
   * @return the contained {@link T} instance, never null
   */
  public T get() {
    this.initialize();
    return this.value;
  }
}
