package core.destroyableReference;

public final class DestroyableReferenceUtils
{
	public static final DestroyableReference<?> checkDestroyable(DestroyableReference<?> destroyableReference)
	{
		if(destroyableReference != null)
		{
			if(destroyableReference.checkDestroyable() != null)
				return destroyableReference;
		}
		
		return null;
	}
}
