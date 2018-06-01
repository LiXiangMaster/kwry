package hwd.kuworuye.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public abstract class ObjectUtils
{
	public final static Object NULL = null;

	public static boolean isNull(Object o)
	{
		return (o == null);
	}

	public static boolean notNull(Object o)
	{
		return !isNull(o);
	}

	public static boolean isEmpty(Object o)
	{
		if (isNull(o))
		{
			return true;
		}

		if (o instanceof String)
		{
			return "".equals(o.toString());
		} else if (o instanceof Collection<?>)
		{
			return ((Collection<?>) o).isEmpty();
		} else if (o instanceof Map<?, ?>)
		{
			return ((Map<?, ?>) o).isEmpty();
		} else if (o.getClass().isArray())
		{
			return Array.getLength(o) == 0;
		}

		return true;
	}

	public static boolean notEmpty(Object o)
	{
		return !isEmpty(o);
	}

	public static <T> T nullSafe(T actual, T safe)
	{
		return actual == null ? safe : actual;
	}
}