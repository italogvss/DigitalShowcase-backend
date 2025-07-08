package italogvss.digitalshowcase.interfaces;

import java.util.List;

public interface MapperInterface<M,D> {
  public D toDTO(M model);
  public List<D> toDTOList(List<M> model);

}
