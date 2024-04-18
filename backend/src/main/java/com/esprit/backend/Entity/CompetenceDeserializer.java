package com.esprit.backend.Entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CompetenceDeserializer extends StdDeserializer<Set<String>> {

  public CompetenceDeserializer() {
    this(null);
  }

  public CompetenceDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public Set<String> deserialize(JsonParser jp, DeserializationContext ctxt)
    throws IOException {
    JsonNode node = jp.getCodec().readTree(jp);
    Set<String> competence = new HashSet<>();
    for (JsonNode jsonNode : node) {
      competence.add(jsonNode.asText());
    }
    return competence;
  }
}
