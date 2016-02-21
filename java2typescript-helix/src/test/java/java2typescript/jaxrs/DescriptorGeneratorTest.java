/*******************************************************************************
 * Copyright 2013 Raphael Jolivet
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package java2typescript.jaxrs;

import com.cg.helix.mib.annotation.ComponentInterface;
import com.cg.helix.mib.annotation.Input;
import com.cg.helix.schemadictionary.annotation.ComplexType;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java2typescript.jackson.module.DefinitionGenerator;
import java2typescript.jackson.module.grammar.Module;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class DescriptorGeneratorTest {

    private Writer out;

    static class MyObject {
        public String field;
    }

    @ComplexType(name = AdministrationDTO.NAME)
    class AdministrationDTO {
        public static final String NAME = "/cgm/g3his/mor/AdministrationDTO";

        private String id;
        private String name;
        private List<DoseDTO> doses;
        private DoseDTO defaultDose;
        private PlannedDoseDTO plannedDose;
        private LocalDateTime startDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<DoseDTO> getDoses() {
            return doses;
        }

        public void setDoses(List<DoseDTO> doses) {
            this.doses = doses;
        }

        public PlannedDoseDTO getPlannedDose() {
            return plannedDose;
        }

        public void setPlannedDose(PlannedDoseDTO plannedDose) {
            this.plannedDose = plannedDose;
        }

        public DoseDTO getDefaultDose() {
            return defaultDose;
        }

        public void setDefaultDose(DoseDTO defaultDose) {
            this.defaultDose = defaultDose;
        }

        public LocalDateTime getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDateTime startDate) {
            this.startDate = startDate;
        }
    }

    @ComplexType(name = DoseDTO.NAME)
    class DoseDTO {
        public static final String NAME = "/cgm/g3his/mor/DoseDTO";

        private String id;
        private Integer dose;
        private String unit;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getDose() {
            return dose;
        }

        public void setDose(Integer dose) {
            this.dose = dose;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }

    @ComplexType(name = PlannedDoseDTO.NAME)
    class PlannedDoseDTO {
        public static final String NAME = "/cgm/g3his/mor/PlannedDoseDTO";

        String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    @ComponentInterface(name = ExampleService.NAME)
    private interface ExampleService {

        String NAME = "/cgm/g3his/mor/ExampleService";

        //void delete(AdministrationDTO administration);

        String saveOrUpdate(String queryParam, String id, Integer formParam, String postPayload);

        void find(@Input(name = "q1") String queryParam, @Input(name = "id") String id,
                  @Input(name = "formParam") Integer formParam, MyObject postPayload);

    }


    @BeforeClass
    public void setUp() {
        out = new OutputStreamWriter(System.out);
    }


    //@Test
    public void testHelixComponentInterfaceTypescriptGenerate() throws JsonGenerationException, JsonMappingException, IOException {

        ServiceDescriptorGenerator descGen = new ServiceDescriptorGenerator(
                singletonList(ExampleService.class));

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("custom-mapping");

        mapper.registerModule(module);

        Module tsModule = descGen.generateTypeScript("modName");
        tsModule.write(out);
    }

    @Test
    public void testHelixComplexTypeTypescriptGenerate() throws JsonGenerationException, JsonMappingException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("custom-mapping");

        mapper.registerModule(module);

        DefinitionGenerator definitionGenerator = new DefinitionGenerator(mapper);

        Module tsModule = definitionGenerator.generateTypeScript("modName", singletonList(AdministrationDTO.class), null, ComplexType.class);

        tsModule.write(out);
    }

    @Test
    public void testHelixComplexTypeTypescriptGenerateWithMultipleModules() throws JsonGenerationException, JsonMappingException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("custom-mapping");

        mapper.registerModule(module);

        DefinitionGenerator definitionGenerator = new DefinitionGenerator(mapper);

        Module tsModule = definitionGenerator.generateTypeScript("tsm", singletonList(DoseDTO.class), null, ComplexType.class);
        tsModule.write(out);

        Module medModule = definitionGenerator.generateTypeScript("med", singletonList(AdministrationDTO.class), null, ComplexType.class, asList(tsModule));
        medModule.write(out);
    }

    //@Test
    public void testTypescriptGenerate() throws JsonGenerationException, JsonMappingException, IOException {

        /*ServiceDescriptorGenerator descGen = new ServiceDescriptorGenerator(
                Collections.singletonList(PeopleRestService.class));

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("custom-mapping");

        mapper.registerModule(module);

        Module tsModule = descGen.generateTypeScript("modName");
        tsModule.write(out);*/
    }
}
