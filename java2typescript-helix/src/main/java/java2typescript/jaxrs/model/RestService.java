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
package java2typescript.jaxrs.model;

import com.esotericsoftware.kryo.Kryo;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;

/**
 * Root descrpition of a service
 */
public class RestService {

    private String name;
    private String path;
    private final Map<String, Method> methods = new HashMap<String, Method>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, Method> getMethods() {
        return methods;
    }

    /**
     * Dump a JSON representation of the REST services
     */
    static public void toJSON(Collection<RestService> services, Writer writer) throws JsonGenerationException,
            JsonMappingException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(INDENT_OUTPUT, true);

        List<RestService> restServivcesWithoutParams = new ArrayList<RestService>();
        for (RestService restService : services) {
            restServivcesWithoutParams.add(copyWithoutContextParams(restService));
        }

        mapper.writeValue(writer, restServivcesWithoutParams);
    }

    /**
     * Re,ove @Context parameters from JSON definition before rendering
     */
    static private RestService copyWithoutContextParams(RestService restService) {
        Kryo kryo = new Kryo();
        RestService res = kryo.copy(restService);
        for (Method method : res.getMethods().values()) {
            Iterator<Param> paramsIt = method.getParams().iterator();
            while (paramsIt.hasNext()) {
                Param param = paramsIt.next();
                if (param.isContext()) {
                    paramsIt.remove();
                }
            }
        }
        return res;
    }
}