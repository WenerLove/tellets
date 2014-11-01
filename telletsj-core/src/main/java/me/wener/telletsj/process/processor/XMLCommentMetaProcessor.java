package me.wener.telletsj.process.processor;

import com.google.inject.Singleton;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Named;
import me.wener.telletsj.process.ContentInfo;
import me.wener.telletsj.process.NoMetaException;
import me.wener.telletsj.process.ProcessException;
import me.wener.telletsj.process.ProcessUtil;

@Named
@Singleton
public class XMLCommentMetaProcessor extends ExtensionDetectProcessor
{
    private final static XMLCommentMetaProcessor INSTANCE = new XMLCommentMetaProcessor();

    private final static Pattern SECTION_SPLITTER =
            Pattern.compile("^\\s*<!--\\s*(?<type>more|summary|paging)\\s*-->\\s*$", Pattern.MULTILINE);
    private final static Pattern SINGLE_META =
            Pattern.compile("^\\s*<!--(?<key>[^:]+):(?<value>.*?)-->\\s*$", Pattern.MULTILINE);
    private final static Pattern ALL_META =
            Pattern.compile("(" + SINGLE_META.pattern() + ")+", Pattern.MULTILINE);

    public XMLCommentMetaProcessor()
    {
        super("markdown|md|mdown|mkd|mkdn|mdwn|mdtxt|mdtext".split("\\|"));
    }

    @Override
    public ContentInfo process(final String content) throws ProcessException
    {
        String metaContent;
        String restContent;
        ContentInfo info = new ContentInfo().rawContent(content);
        {
            Matcher matcher = ALL_META.matcher(content);
            if (!matcher.find())
                throw new NoMetaException();
            int lastEnd = matcher.end();
            while (matcher.find())
                lastEnd = matcher.end();

            metaContent = content.substring(0, lastEnd);
            restContent = content.substring(lastEnd);
        }
        {
            Matcher matcher = SINGLE_META.matcher(metaContent);
            while (matcher.find())
            {
                String key = matcher.group("key").trim();
                String value = matcher.group("value").trim();
                info.meta().put(key, value);
            }
        }
        {
            Matcher matcher = SECTION_SPLITTER.matcher(restContent);
            int lastEnd = 0;
            while (matcher.find())
            {
                String type = matcher.group("type");
                String sectionContent = restContent
                        .substring(lastEnd, matcher.start())
                        .trim();
                info.sections().get(type).add(ProcessUtil.trim(sectionContent));
                lastEnd = matcher.end();
            }

            if (restContent.length() != lastEnd)
                info.restContent(ProcessUtil.trim(restContent.substring(lastEnd)));
        }
        return info;
    }
}