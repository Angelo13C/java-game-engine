package core.destroyableReference;

public interface DestroyableReference<T>
{
	void destroy();
	T checkDestroyable();
}
