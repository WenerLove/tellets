package me.wener.telletsj.data.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;
import me.wener.telletsj.data.AliasLabel;

@Data
@Accessors(chain = true)
public class SimpleAliasLabel<T> implements AliasLabel<T>
{
    private final Set<String> aliases = Sets.newHashSet();
    private String name;

    /**
     * @param more 添加的别名
     * @return 本实例
     */
    @SuppressWarnings("unchecked")
    public T alias(String more)
    {
        aliases.add(more);
        return (T) this;
    }

    @Override
    public Set<String> aliases()
    {
        return aliases;
    }
}