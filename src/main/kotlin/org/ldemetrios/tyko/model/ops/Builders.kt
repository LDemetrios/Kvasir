package org.ldemetrios.tyko.model

import org.ldemetrios.tyko.model.t
import org.ldemetrios.tyko.model.text

open class SequenceBuilder {
    protected val list: MutableList<TContent> = mutableListOf()

    fun create() = TSequence(TArray(list))

    private val continuer = BuilderContinuer()

    open operator fun TContent.unaryPlus(): BuilderContinuer {
        list.add(this)
        return continuer
    }

    open operator fun String.unaryPlus(): BuilderContinuer {
        list.add(TText(text = this.t))
        return continuer
    }

    open operator fun TStr.unaryPlus(): BuilderContinuer {
        list.add(TText(text = this))
        return continuer
    }

    open inner class BuilderContinuer {
        open operator fun plus(x: TContent): BuilderContinuer = also { list.add(x) }
        open operator fun plus(x: String): BuilderContinuer = also { list.add(TText(text = x.t)) }
        open operator fun plus(x: TStr): BuilderContinuer = also { list.add(TText(text = x)) }
    }
}

class MathSequenceBuilder : SequenceBuilder() {
    private val continuer = MathBuilderContinuer()

    override operator fun TContent.unaryPlus(): MathBuilderContinuer {
        list.add(this)
        return continuer
    }

    override operator fun String.unaryPlus(): MathBuilderContinuer {
        list.add(TText(text = this.t))
        return continuer
    }

    override operator fun TStr.unaryPlus(): MathBuilderContinuer {
        list.add(TText(text = this))
        return continuer
    }

    operator fun TContent.div(denom: TContent) = TMathFrac(this, denom)
    operator fun TContent.div(denom: TStr) = TMathFrac(this, denom.text)
    operator fun TContent.div(denom: String) = TMathFrac(this, denom.text)

    operator fun TStr.div(denom: TContent) = TMathFrac(this.text, denom)
    operator fun TStr.div(denom: TStr) = TMathFrac(this.text, denom.text)
    operator fun TStr.div(denom: String) = TMathFrac(this.text, denom.text)

    operator fun String.div(denom: TContent) = TMathFrac(this.text, denom)
    operator fun String.div(denom: TStr) = TMathFrac(this.text, denom.text)
    operator fun String.div(denom: String) = TMathFrac(this.text, denom.text)

    inner class MathBuilderContinuer : BuilderContinuer() {
        override operator fun plus(x: TContent): BuilderContinuer = also { list.add(x) }
        override operator fun plus(x: String): BuilderContinuer = also { list.add(TText(text = x.t)) }
        override operator fun plus(x: TStr): BuilderContinuer = also { list.add(TText(text = x)) }

        operator fun div(x: TContent) = also {list.add(list.removeLast() / x)}
        operator fun div(x: String) = also {list.add(list.removeLast() / x)}
        operator fun div(x: TStr) = also {list.add(list.removeLast() / x)}
    }
}