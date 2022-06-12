package io.omnika.common.core.converters;

/** The abstraction for mappers based on entities. It is needed to
 * standardize mappers which created exactly for model entities.
 * The main problem solved with this interface is
 * using inside mapper another mapper. For example
 * entity A have many to one relation with entity B.
 * So inside converter to create entity A we have to
 * add entity B to constructor. To transform B dto to
 * domain we can use inside A converter another B converter.
 * But we have to be sure that this have method toDomain.
 * This interface simplify searching and calling needed method for
 * converting needed domain to dto and vise-versa.
 *
 * Generics: D = domain, T = transfer (DTO)
 * */
public interface BasicEntityMapper<D, T> {

    D toDomain(T dto);

    T toDto(D domain);
}
