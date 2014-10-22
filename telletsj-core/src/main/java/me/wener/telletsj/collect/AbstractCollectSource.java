package me.wener.telletsj.collect;

import com.google.common.base.Preconditions;
import lombok.Getter;

public abstract class AbstractCollectSource implements CollectSource
{
    @Getter
    private CollectSourceConfig config;

    public void setConfig(CollectSourceConfig config)
    {
        Preconditions.checkState(config == null,"Config already set");
        this.config = config;
    }

    public abstract Iterable<SourceContent> collect();

    @Override
    public boolean isChanged()
    {
        return false;
    }

    @Override
    public void fillContent(SourceContent source)
    {

    }
}
