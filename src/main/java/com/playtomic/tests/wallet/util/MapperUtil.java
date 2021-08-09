package com.playtomic.tests.wallet.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class MapperUtil {

  private static ModelMapper modelMapper;

  static {
    modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
  }

  public static <R, T> R map(final T inpClass, Class<R> outClass) {
    if (inpClass == null) {
      return null;
    }
    return modelMapper.map(inpClass, outClass);
  }

  public static <R, T> List<R> mapAll(final Collection<T> inpClassList, Class<R> outCLass) {
    if (inpClassList == null) {
      return null;
    }
    return inpClassList.stream().map(inpClass -> map(inpClass, outCLass))
        .collect(Collectors.toList());
  }
}
