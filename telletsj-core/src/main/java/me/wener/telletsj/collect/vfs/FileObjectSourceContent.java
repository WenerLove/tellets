package me.wener.telletsj.collect.vfs;

import com.google.common.base.Preconditions;
import lombok.SneakyThrows;
import me.wener.telletsj.collect.impl.SourceContent;
import me.wener.telletsj.util.IO;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileType;

public class FileObjectSourceContent extends SourceContent
{
    private final FileObject file;

    public FileObjectSourceContent(FileObject file) throws FileSystemException
    {
        this.file = file;
        Preconditions.checkArgument(file.getType() == FileType.FILE, "Must be file object");
        setFilename(file.getName().getBaseName());
    }

    @Override
    @SneakyThrows
    protected void fillContent()
    {
        String content = IO.toString(file.getContent().getInputStream());
        setContent(content);
    }
}
