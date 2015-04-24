package power.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent=true)
@RequiredArgsConstructor
public class KeyValue<F, S> {
	final F first;
	final S second;
}