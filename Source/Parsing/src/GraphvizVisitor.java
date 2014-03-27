/*
  Copyright 2014 Sylvain Hallé
  Laboratoire d'informatique formelle
  Université du Québec à Chicoutimi, Canada
  
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
      http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/
import java.util.Stack;

import ca.uqac.lif.bnf.ParseNode;
import ca.uqac.lif.bnf.ParseNodeVisitor;

public class GraphvizVisitor implements ParseNodeVisitor
{
  private Stack<Integer> m_parents;
  
  private int m_nodeCount = 0;
  
  StringBuilder m_output;
  
  public GraphvizVisitor()
  {
    super();
    m_parents = new Stack<Integer>();
    m_output = new StringBuilder();
  }

  @Override
  public void visit(final ParseNode node)
  {
    int cur_node = m_nodeCount++;
    if (!m_parents.isEmpty())
    {
      int parent = m_parents.peek();
      m_output.append(parent).append(" -> ").append(cur_node).append(";\n");
    }
    String shape = "oval";
    String color = "white";
    String fillcolor = "blue";
    String label = node.getValue();
    if (label == null)
    {
      label = node.getToken();
      shape = "rect";
      fillcolor = "white";
      color = "black";
    }
    m_output.append("  ").append(cur_node).append(" [fontcolor=\"").append(color).append("\",style=\"filled\",fillcolor=\"").append(fillcolor).append("\",shape=\"").append(shape).append("\",label=\"").append(label).append("\"];\n");
    m_parents.push(cur_node);
  }
  
  @Override
  public void pop()
  {
    m_parents.pop();
  }
  
  public String toDot()
  {
    StringBuilder out = new StringBuilder();
    out.append("digraph G {\n").append(m_output).append("};");
    return out.toString();
  }

}