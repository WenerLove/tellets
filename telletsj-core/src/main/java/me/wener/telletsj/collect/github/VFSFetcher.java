package me.wener.telletsj.collect.github;

import java.io.IOException;
import me.wener.telletsj.util.IO;
import org.apache.commons.vfs2.FileSystemManager;

public class VFSFetcher implements Fetcher
{
    private final FileSystemManager fsm;

    public VFSFetcher(FileSystemManager fsm) {this.fsm = fsm;}

    @Override
    public String fetch(String url) throws IOException
    {
        return IO.toString(fsm.resolveFile(url).getContent().getInputStream());
    }
}
