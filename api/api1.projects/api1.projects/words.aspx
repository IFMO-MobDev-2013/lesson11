<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="words.aspx.cs" Inherits="api1.projects.words" %>
<%@ Import Namespace="System.Collections.Generic" %>
<%@ Import Namespace="System.Data" %>
<%@ Import Namespace="System.Data.SqlClient" %>
<%@ Import Namespace="System.IO" %>
<%@ Import Namespace="System.Web.Script.Serialization" %>

<<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<script runat="server">
    protected void Page_Load(object sender, EventArgs ec)
    {
        using (SqlConnection cn = new SqlConnection("Data Source=localhost;Initial Catalog=ru_mermakov_projects_api1;Persist Security Info=True;User ID=ermakov;Password=Senokos95")) //change as needed
        {
            using (StreamReader sr = new StreamReader(Request.InputStream, Encoding.UTF8))
            {
                Response.ContentType = "text/plain";
                string s_cat = Request.QueryString["cat"];
                string c = "exec getWords @cat = " + s_cat;
                if (c == null)
                    c = sr.ReadToEnd();
                try
                {
                    SqlCommand cmd = new SqlCommand(c, cn);
                    cn.Open();
                    SqlDataReader rdr = cmd.ExecuteReader(CommandBehavior.CloseConnection);
                    List<Dictionary<string, object>> list = new List<Dictionary<string, object>>();
                    while (rdr.Read())
                    {
                        Dictionary<string, object> d = new Dictionary<string, object>(rdr.FieldCount);
                        for (int i = 0; i < rdr.FieldCount; i++)
                        {
                            d[rdr.GetName(i)] = rdr.GetValue(i);
                        }
                        list.Add(d);
                    }
                    JavaScriptSerializer j = new JavaScriptSerializer();
                    Response.Write(j.Serialize(list.ToArray()));

                }
                catch (Exception e)
                {
                    Response.TrySkipIisCustomErrors = true;
                    Response.StatusCode = 500;
                    Response.Write("Error occurred. Query=" + c + "\n");
                    Response.Write(e.ToString());

                }
                Response.End();
            }
        }
    }
</script>
