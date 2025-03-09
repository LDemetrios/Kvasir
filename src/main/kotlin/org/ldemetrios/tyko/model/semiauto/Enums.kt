@file:Suppress("unused", "RedundantVisibilityModifier")

package org.ldemetrios.tyko.model

import kotlin.Any
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.collections.Map
import kotlin.reflect.KType
import kotlin.reflect.typeOf
import org.ldemetrios.js.JSBoolean
import org.ldemetrios.js.JSNumber
import org.ldemetrios.js.JSObject
import org.ldemetrios.js.JSString
import org.ldemetrios.js.JSUndefined
import org.ldemetrios.utilities.cast
import org.ldemetrios.utilities.castUnchecked

@TEnumType(["bibliography-style"])
public interface TBibliographyStyle : TStr, TAutoOrBibliographyStyle {
    public companion object {
        public val Alphanumeric: TBibliographyStyle = alphanumeric

        public val AmericanAnthropologicalAssociation: TBibliographyStyle =
                americanAnthropologicalAssociation

        public val AmericanChemicalSociety: TBibliographyStyle = americanChemicalSociety

        public val AmericanGeophysicalUnion: TBibliographyStyle = americanGeophysicalUnion

        public val AmericanInstituteOfAeronauticsAndAstronautics: TBibliographyStyle =
                americanInstituteOfAeronauticsAndAstronautics

        public val AmericanInstituteOfPhysics: TBibliographyStyle = americanInstituteOfPhysics

        public val AmericanMedicalAssociation: TBibliographyStyle = americanMedicalAssociation

        public val AmericanMeteorologicalSociety: TBibliographyStyle = americanMeteorologicalSociety

        public val AmericanPhysicsSociety: TBibliographyStyle = americanPhysicsSociety

        public val AmericanPhysiologicalSociety: TBibliographyStyle = americanPhysiologicalSociety

        public val AmericanPoliticalScienceAssociation: TBibliographyStyle =
                americanPoliticalScienceAssociation

        public val AmericanPsychologicalAssociation: TBibliographyStyle =
                americanPsychologicalAssociation

        public val AmericanSocietyForMicrobiology: TBibliographyStyle = americanSocietyForMicrobiology

        public val AmericanSocietyOfCivilEngineers: TBibliographyStyle = americanSocietyOfCivilEngineers

        public val AmericanSocietyOfMechanicalEngineers: TBibliographyStyle =
                americanSocietyOfMechanicalEngineers

        public val AmericanSociologicalAssociation: TBibliographyStyle = americanSociologicalAssociation

        public val AngewandteChemie: TBibliographyStyle = angewandteChemie

        public val AnnualReviews: TBibliographyStyle = annualReviews

        public val AnnualReviewsAuthorDate: TBibliographyStyle = annualReviewsAuthorDate

        public val AssociacaoBrasileiraDeNormasTecnicas: TBibliographyStyle =
                associacaoBrasileiraDeNormasTecnicas

        public val AssociationForComputingMachinery: TBibliographyStyle =
                associationForComputingMachinery

        public val BiomedCentral: TBibliographyStyle = biomedCentral

        public val BristolUniversityPress: TBibliographyStyle = bristolUniversityPress

        public val BritishMedicalJournal: TBibliographyStyle = britishMedicalJournal

        public val Cell: TBibliographyStyle = cell

        public val ChicagoAuthorDate: TBibliographyStyle = chicagoAuthorDate

        public val ChicagoFullnotes: TBibliographyStyle = chicagoFullnotes

        public val ChicagoNotes: TBibliographyStyle = chicagoNotes

        public val Copernicus: TBibliographyStyle = copernicus

        public val CouncilOfScienceEditors: TBibliographyStyle = councilOfScienceEditors

        public val CouncilOfScienceEditorsAuthorDate: TBibliographyStyle =
                councilOfScienceEditorsAuthorDate

        public val CurrentOpinion: TBibliographyStyle = currentOpinion

        public val DeutscheGesellschaftFürPsychologie: TBibliographyStyle =
                deutscheGesellschaftFürPsychologie

        public val DeutscheSprache: TBibliographyStyle = deutscheSprache

        public val ElsevierHarvard: TBibliographyStyle = elsevierHarvard

        public val ElsevierVancouver: TBibliographyStyle = elsevierVancouver

        public val ElsevierWithTitles: TBibliographyStyle = elsevierWithTitles

        public val Frontiers: TBibliographyStyle = frontiers

        public val FutureMedicine: TBibliographyStyle = futureMedicine

        public val FutureScience: TBibliographyStyle = futureScience

        public val Gb77142005Numeric: TBibliographyStyle = gb77142005Numeric

        public val Gb77142015AuthorDate: TBibliographyStyle = gb77142015AuthorDate

        public val Gb77142015Note: TBibliographyStyle = gb77142015Note

        public val Gb77142015Numeric: TBibliographyStyle = gb77142015Numeric

        public val GostR7052008Numeric: TBibliographyStyle = gostR7052008Numeric

        public val HarvardCiteThemRight: TBibliographyStyle = harvardCiteThemRight

        public val InstituteOfElectricalAndElectronicsEngineers: TBibliographyStyle =
                instituteOfElectricalAndElectronicsEngineers

        public val InstituteOfPhysicsNumeric: TBibliographyStyle = instituteOfPhysicsNumeric

        public val Iso690AuthorDate: TBibliographyStyle = iso690AuthorDate

        public val Iso690Numeric: TBibliographyStyle = iso690Numeric

        public val Karger: TBibliographyStyle = karger

        public val MaryAnnLiebertVancouver: TBibliographyStyle = maryAnnLiebertVancouver

        public val ModernHumanitiesResearchAssociation: TBibliographyStyle =
                modernHumanitiesResearchAssociation

        public val ModernLanguageAssociation: TBibliographyStyle = modernLanguageAssociation

        public val ModernLanguageAssociation8: TBibliographyStyle = modernLanguageAssociation8

        public val MultidisciplinaryDigitalPublishingInstitute: TBibliographyStyle =
                multidisciplinaryDigitalPublishingInstitute

        public val Nature: TBibliographyStyle = nature

        public val Pensoft: TBibliographyStyle = pensoft

        public val PublicLibraryOfScience: TBibliographyStyle = publicLibraryOfScience

        public val RoyalSocietyOfChemistry: TBibliographyStyle = royalSocietyOfChemistry

        public val SageVancouver: TBibliographyStyle = sageVancouver

        public val Sist02: TBibliographyStyle = sist02

        public val Spie: TBibliographyStyle = spie

        public val SpringerBasic: TBibliographyStyle = springerBasic

        public val SpringerBasicAuthorDate: TBibliographyStyle = springerBasicAuthorDate

        public val SpringerFachzeitschriftenMedizinPsychologie: TBibliographyStyle =
                springerFachzeitschriftenMedizinPsychologie

        public val SpringerHumanitiesAuthorDate: TBibliographyStyle = springerHumanitiesAuthorDate

        public val SpringerLectureNotesInComputerScience: TBibliographyStyle =
                springerLectureNotesInComputerScience

        public val SpringerMathphys: TBibliographyStyle = springerMathphys

        public val SpringerSocpsychAuthorDate: TBibliographyStyle = springerSocpsychAuthorDate

        public val SpringerVancouver: TBibliographyStyle = springerVancouver

        public val TaylorAndFrancisChicagoAuthorDate: TBibliographyStyle =
                taylorAndFrancisChicagoAuthorDate

        public val TaylorAndFrancisNationalLibraryOfMedicine: TBibliographyStyle =
                taylorAndFrancisNationalLibraryOfMedicine

        public val TheInstitutionOfEngineeringAndTechnology: TBibliographyStyle =
                theInstitutionOfEngineeringAndTechnology

        public val TheLancet: TBibliographyStyle = theLancet

        public val Thieme: TBibliographyStyle = thieme

        public val Trends: TBibliographyStyle = trends

        public val TurabianAuthorDate: TBibliographyStyle = turabianAuthorDate

        public val TurabianFullnote8: TBibliographyStyle = turabianFullnote8

        public val Vancouver: TBibliographyStyle = vancouver

        public val VancouverSuperscript: TBibliographyStyle = vancouverSuperscript
    }
}

internal class TBibliographyStyleImpl(
    override val strValue: String,
) : TBibliographyStyle {
    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}

@TEnumType(["cite-form", "math.class-class", "ref-form", "text-style"])
public interface TCiteFormAndMathClassClassAndRefFormAndTextStyle : TStr, TCiteForm,
        TMathClassClass, TTextStyle, TRefForm

internal class TCiteFormAndMathClassClassAndRefFormAndTextStyleImpl(
    override val strValue: String,
) : TCiteFormAndMathClassClassAndRefFormAndTextStyle {
    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}

@TEnumType(["cite-form"])
public interface TCiteForm : TStr, TCiteFormOrNone {
    public companion object {
        public val Normal: TCiteForm = normal

        public val Prose: TCiteForm = prose

        public val Full: TCiteForm = full

        public val Author: TCiteForm = author

        public val Year: TCiteForm = year
    }
}

internal class TCiteFormImpl(
    override val strValue: String,
) : TCiteForm {
    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}

@TEnumType(["curve-close-mode", "image-scaling"])
public interface TCurveCloseModeAndImageScaling : TStr, TCurveCloseMode, TImageScaling

internal class TCurveCloseModeAndImageScalingImpl(
    override val strValue: String,
) : TCurveCloseModeAndImageScaling {
    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}

@TEnumType(["curve-close-mode"])
public interface TCurveCloseMode : TStr {
    public companion object {
        public val Smooth: TCurveCloseMode = smooth

        public val Straight: TCurveCloseMode = straight
    }
}

internal class TCurveCloseModeImpl(
    override val strValue: String,
) : TCurveCloseMode {
    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}

@TEnumType(["figure-scope"])
public interface TFigureScope : TStr {
    public companion object {
        public val Column: TFigureScope = column

        public val Parent: TFigureScope = parent
    }
}

internal class TFigureScopeImpl(
    override val strValue: String,
) : TFigureScope {
    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}

@TEnumType(["fill-rule"])
public interface TFillRule : TStr {
    public companion object {
        public val NonZero: TFillRule = nonZero

        public val EvenOdd: TFillRule = evenOdd
    }
}

internal class TFillRuleImpl(
    override val strValue: String,
) : TFillRule {
    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}

@TEnumType(["image-fit"])
public interface TImageFit : TStr {
    public companion object {
        public val Cover: TImageFit = cover

        public val Contain: TImageFit = contain

        public val Stretch: TImageFit = stretch
    }
}

internal class TImageFitImpl(
    override val strValue: String,
) : TImageFit {
    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}

@TEnumType(["image-format"])
public interface TImageFormat : TStr, TAutoOrDictionaryOrImageFormat<TDynamic> {
    public companion object {
        public val Png: TImageFormat = png

        public val Jpg: TImageFormat = jpg

        public val Gif: TImageFormat = gif

        public val Svg: TImageFormat = svg
    }
}

internal class TImageFormatImpl(
    override val strValue: String,
) : TImageFormat {
    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}

@TEnumType(["image-scaling"])
public interface TImageScaling : TStr, TAutoOrImageScaling {
    public companion object {
        public val Smooth: TImageScaling = smooth

        public val Pixelated: TImageScaling = pixelated
    }
}

internal class TImageScalingImpl(
    override val strValue: String,
) : TImageScaling {
    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}

@TEnumType(["math.class-class"])
public interface TMathClassClass : TStr {
    public companion object {
        public val Normal: TMathClassClass = normal

        public val Punctuation: TMathClassClass = punctuation

        public val Opening: TMathClassClass = opening

        public val Closing: TMathClassClass = closing

        public val Fence: TMathClassClass = fence

        public val Large: TMathClassClass = large

        public val Relation: TMathClassClass = relation

        public val Unary: TMathClassClass = unary

        public val Binary: TMathClassClass = binary

        public val Vary: TMathClassClass = vary
    }
}

internal class TMathClassClassImpl(
    override val strValue: String,
) : TMathClassClass {
    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}

@TEnumType(["page-paper"])
public interface TPagePaper : TStr {
    public companion object {
        public val A0: TPagePaper = a0

        public val A1: TPagePaper = a1

        public val A2: TPagePaper = a2

        public val A3: TPagePaper = a3

        public val A4: TPagePaper = a4

        public val A5: TPagePaper = a5

        public val A6: TPagePaper = a6

        public val A7: TPagePaper = a7

        public val A8: TPagePaper = a8

        public val A9: TPagePaper = a9

        public val A10: TPagePaper = a10

        public val A11: TPagePaper = a11

        public val IsoB1: TPagePaper = isoB1

        public val IsoB2: TPagePaper = isoB2

        public val IsoB3: TPagePaper = isoB3

        public val IsoB4: TPagePaper = isoB4

        public val IsoB5: TPagePaper = isoB5

        public val IsoB6: TPagePaper = isoB6

        public val IsoB7: TPagePaper = isoB7

        public val IsoB8: TPagePaper = isoB8

        public val IsoC3: TPagePaper = isoC3

        public val IsoC4: TPagePaper = isoC4

        public val IsoC5: TPagePaper = isoC5

        public val IsoC6: TPagePaper = isoC6

        public val IsoC7: TPagePaper = isoC7

        public val IsoC8: TPagePaper = isoC8

        public val DinD3: TPagePaper = dinD3

        public val DinD4: TPagePaper = dinD4

        public val DinD5: TPagePaper = dinD5

        public val DinD6: TPagePaper = dinD6

        public val DinD7: TPagePaper = dinD7

        public val DinD8: TPagePaper = dinD8

        public val SisG5: TPagePaper = sisG5

        public val SisE5: TPagePaper = sisE5

        public val AnsiA: TPagePaper = ansiA

        public val AnsiB: TPagePaper = ansiB

        public val AnsiC: TPagePaper = ansiC

        public val AnsiD: TPagePaper = ansiD

        public val AnsiE: TPagePaper = ansiE

        public val ArchA: TPagePaper = archA

        public val ArchB: TPagePaper = archB

        public val ArchC: TPagePaper = archC

        public val ArchD: TPagePaper = archD

        public val ArchE1: TPagePaper = archE1

        public val ArchE: TPagePaper = archE

        public val JisB0: TPagePaper = jisB0

        public val JisB1: TPagePaper = jisB1

        public val JisB2: TPagePaper = jisB2

        public val JisB3: TPagePaper = jisB3

        public val JisB4: TPagePaper = jisB4

        public val JisB5: TPagePaper = jisB5

        public val JisB6: TPagePaper = jisB6

        public val JisB7: TPagePaper = jisB7

        public val JisB8: TPagePaper = jisB8

        public val JisB9: TPagePaper = jisB9

        public val JisB10: TPagePaper = jisB10

        public val JisB11: TPagePaper = jisB11

        public val SacD0: TPagePaper = sacD0

        public val SacD1: TPagePaper = sacD1

        public val SacD2: TPagePaper = sacD2

        public val SacD3: TPagePaper = sacD3

        public val SacD4: TPagePaper = sacD4

        public val SacD5: TPagePaper = sacD5

        public val SacD6: TPagePaper = sacD6

        public val IsoId1: TPagePaper = isoId1

        public val IsoId2: TPagePaper = isoId2

        public val IsoId3: TPagePaper = isoId3

        public val AsiaF4: TPagePaper = asiaF4

        public val JpShirokuBan4: TPagePaper = jpShirokuBan4

        public val JpShirokuBan5: TPagePaper = jpShirokuBan5

        public val JpShirokuBan6: TPagePaper = jpShirokuBan6

        public val JpKiku4: TPagePaper = jpKiku4

        public val JpKiku5: TPagePaper = jpKiku5

        public val JpBusinessCard: TPagePaper = jpBusinessCard

        public val CnBusinessCard: TPagePaper = cnBusinessCard

        public val EuBusinessCard: TPagePaper = euBusinessCard

        public val FrTellière: TPagePaper = frTellière

        public val FrCouronneÉcriture: TPagePaper = frCouronneÉcriture

        public val FrCouronneÉdition: TPagePaper = frCouronneÉdition

        public val FrRaisin: TPagePaper = frRaisin

        public val FrCarré: TPagePaper = frCarré

        public val FrJésus: TPagePaper = frJésus

        public val UkBrief: TPagePaper = ukBrief

        public val UkDraft: TPagePaper = ukDraft

        public val UkFoolscap: TPagePaper = ukFoolscap

        public val UkQuarto: TPagePaper = ukQuarto

        public val UkCrown: TPagePaper = ukCrown

        public val UkBookA: TPagePaper = ukBookA

        public val UkBookB: TPagePaper = ukBookB

        public val UsLetter: TPagePaper = usLetter

        public val UsLegal: TPagePaper = usLegal

        public val UsTabloid: TPagePaper = usTabloid

        public val UsExecutive: TPagePaper = usExecutive

        public val UsFoolscapFolio: TPagePaper = usFoolscapFolio

        public val UsStatement: TPagePaper = usStatement

        public val UsLedger: TPagePaper = usLedger

        public val UsOficio: TPagePaper = usOficio

        public val UsGovLetter: TPagePaper = usGovLetter

        public val UsGovLegal: TPagePaper = usGovLegal

        public val UsBusinessCard: TPagePaper = usBusinessCard

        public val UsDigest: TPagePaper = usDigest

        public val UsTrade: TPagePaper = usTrade

        public val NewspaperCompact: TPagePaper = newspaperCompact

        public val NewspaperBerliner: TPagePaper = newspaperBerliner

        public val NewspaperBroadsheet: TPagePaper = newspaperBroadsheet

        public val Presentation169: TPagePaper = presentation169

        public val Presentation43: TPagePaper = presentation43
    }
}

internal class TPagePaperImpl(
    override val strValue: String,
) : TPagePaper {
    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}

@TEnumType(["pagebreak-to"])
public interface TPagebreakTo : TStr, TNoneOrPagebreakTo {
    public companion object {
        public val Even: TPagebreakTo = even

        public val Odd: TPagebreakTo = odd
    }
}

internal class TPagebreakToImpl(
    override val strValue: String,
) : TPagebreakTo {
    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}

@TEnumType(["par-linebreaks"])
public interface TParLinebreaks : TStr, TAutoOrParLinebreaks {
    public companion object {
        public val Simple: TParLinebreaks = simple

        public val Optimized: TParLinebreaks = optimized
    }
}

internal class TParLinebreaksImpl(
    override val strValue: String,
) : TParLinebreaks {
    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}

@TEnumType(["par.line-numbering-scope"])
public interface TParLineNumberingScope : TStr {
    public companion object {
        public val Document: TParLineNumberingScope = document

        public val Page: TParLineNumberingScope = page
    }
}

internal class TParLineNumberingScopeImpl(
    override val strValue: String,
) : TParLineNumberingScope {
    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}

@TEnumType(["par.line-numbering-scope", "ref-form"])
public interface TParLineNumberingScopeAndRefForm : TStr, TParLineNumberingScope, TRefForm

internal class TParLineNumberingScopeAndRefFormImpl(
    override val strValue: String,
) : TParLineNumberingScopeAndRefForm {
    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}

@TEnumType(["pdf-embed-relationship"])
public interface TPdfEmbedRelationship : TStr {
    public companion object {
        public val Source: TPdfEmbedRelationship = source

        public val Data: TPdfEmbedRelationship = data

        public val Alternative: TPdfEmbedRelationship = alternative

        public val Supplement: TPdfEmbedRelationship = supplement
    }
}

internal class TPdfEmbedRelationshipImpl(
    override val strValue: String,
) : TPdfEmbedRelationship {
    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}

@TEnumType(["text-bottom-edge", "text-top-edge"])
public interface TTextBottomEdgeAndTextTopEdge : TStr, TTextBottomEdge, TTextTopEdge

internal class TTextBottomEdgeAndTextTopEdgeImpl(
    override val strValue: String,
) : TTextBottomEdgeAndTextTopEdge {
    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}

@TEnumType(["text-bottom-edge"])
public interface TTextBottomEdge : TStr, TLengthOrTextBottomEdge {
    public companion object {
        public val Baseline: TTextBottomEdge = baseline

        public val Descender: TTextBottomEdge = descender

        public val Bounds: TTextBottomEdge = bounds
    }
}

internal class TTextBottomEdgeImpl(
    override val strValue: String,
) : TTextBottomEdge {
    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}

@TEnumType(["text-number-type"])
public interface TTextNumberType : TStr, TAutoOrTextNumberType {
    public companion object {
        public val Lining: TTextNumberType = lining

        public val OldStyle: TTextNumberType = oldStyle
    }
}

internal class TTextNumberTypeImpl(
    override val strValue: String,
) : TTextNumberType {
    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}

@TEnumType(["text-number-width"])
public interface TTextNumberWidth : TStr, TAutoOrTextNumberWidth {
    public companion object {
        public val Proportional: TTextNumberWidth = proportional

        public val Tabular: TTextNumberWidth = tabular
    }
}

internal class TTextNumberWidthImpl(
    override val strValue: String,
) : TTextNumberWidth {
    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}

@TEnumType(["text-style"])
public interface TTextStyle : TStr {
    public companion object {
        public val Normal: TTextStyle = normal

        public val Italic: TTextStyle = italic

        public val Oblique: TTextStyle = oblique
    }
}

internal class TTextStyleImpl(
    override val strValue: String,
) : TTextStyle {
    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}

@TEnumType(["text-top-edge"])
public interface TTextTopEdge : TStr, TLengthOrTextTopEdge {
    public companion object {
        public val Ascender: TTextTopEdge = ascender

        public val CapHeight: TTextTopEdge = capHeight

        public val XHeight: TTextTopEdge = xHeight

        public val Baseline: TTextTopEdge = baseline

        public val Bounds: TTextTopEdge = bounds
    }
}

internal class TTextTopEdgeImpl(
    override val strValue: String,
) : TTextTopEdge {
    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}

@TEnumType(["text-weight"])
public interface TTextWeight : TStr, TIntOrTextWeight {
    public companion object {
        public val Thin: TTextWeight = thin

        public val Extralight: TTextWeight = extralight

        public val Light: TTextWeight = light

        public val Regular: TTextWeight = regular

        public val Medium: TTextWeight = medium

        public val Semibold: TTextWeight = semibold

        public val Bold: TTextWeight = bold

        public val Extrabold: TTextWeight = extrabold

        public val Black: TTextWeight = black
    }
}

internal class TTextWeightImpl(
    override val strValue: String,
) : TTextWeight {
    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}

@TEnumType(["ref-form"])
public interface TRefForm : TStr {
    public companion object {
        public val Normal: TRefForm = normal

        public val Page: TRefForm = page
    }
}

internal class TRefFormImpl(
    override val strValue: String,
) : TRefForm {
    override fun equals(other: Any?): Boolean = this === other || other is TStr && this.strValue ==
            other.strValue

    override fun hashCode(): Int = strValue.hashCode()
}

@TEnumType(["bibliography-style", "cite-form", "figure-scope", "par-linebreaks",
        "par.line-numbering-scope", "text-style", "text-weight", "text-top-edge", "text-bottom-edge",
        "text-number-type", "text-number-width", "math.class-class", "page-paper", "pagebreak-to",
        "image-format", "image-fit", "fill-rule", "ref-form", "image-scaling", "h-alignment",
        "v-alignment", "curve-close-mode", "pdf-embed-relationship"])
public interface DynamicEnum : TBibliographyStyle, TCiteFormAndMathClassClassAndRefFormAndTextStyle,
        TCiteForm, TCurveCloseModeAndImageScaling, TCurveCloseMode, TFigureScope, TFillRule, TImageFit,
        TImageFormat, TImageScaling, TMathClassClass, TPagePaper, TPagebreakTo, TParLinebreaks,
        TParLineNumberingScope, TParLineNumberingScopeAndRefForm, TPdfEmbedRelationship,
        TTextBottomEdgeAndTextTopEdge, TTextBottomEdge, TTextNumberType, TTextNumberWidth, TTextStyle,
        TTextTopEdge, TTextWeight, TRefForm, THAlignment, TVAlignment {
    override fun type(): TType
}

private val alphanumeric: TBibliographyStyle = TBibliographyStyleImpl("alphanumeric")

private val americanAnthropologicalAssociation: TBibliographyStyle =
        TBibliographyStyleImpl("american-anthropological-association")

private val americanChemicalSociety: TBibliographyStyle =
        TBibliographyStyleImpl("american-chemical-society")

private val americanGeophysicalUnion: TBibliographyStyle =
        TBibliographyStyleImpl("american-geophysical-union")

private val americanInstituteOfAeronauticsAndAstronautics: TBibliographyStyle =
        TBibliographyStyleImpl("american-institute-of-aeronautics-and-astronautics")

private val americanInstituteOfPhysics: TBibliographyStyle =
        TBibliographyStyleImpl("american-institute-of-physics")

private val americanMedicalAssociation: TBibliographyStyle =
        TBibliographyStyleImpl("american-medical-association")

private val americanMeteorologicalSociety: TBibliographyStyle =
        TBibliographyStyleImpl("american-meteorological-society")

private val americanPhysicsSociety: TBibliographyStyle =
        TBibliographyStyleImpl("american-physics-society")

private val americanPhysiologicalSociety: TBibliographyStyle =
        TBibliographyStyleImpl("american-physiological-society")

private val americanPoliticalScienceAssociation: TBibliographyStyle =
        TBibliographyStyleImpl("american-political-science-association")

private val americanPsychologicalAssociation: TBibliographyStyle =
        TBibliographyStyleImpl("american-psychological-association")

private val americanSocietyForMicrobiology: TBibliographyStyle =
        TBibliographyStyleImpl("american-society-for-microbiology")

private val americanSocietyOfCivilEngineers: TBibliographyStyle =
        TBibliographyStyleImpl("american-society-of-civil-engineers")

private val americanSocietyOfMechanicalEngineers: TBibliographyStyle =
        TBibliographyStyleImpl("american-society-of-mechanical-engineers")

private val americanSociologicalAssociation: TBibliographyStyle =
        TBibliographyStyleImpl("american-sociological-association")

private val angewandteChemie: TBibliographyStyle = TBibliographyStyleImpl("angewandte-chemie")

private val annualReviews: TBibliographyStyle = TBibliographyStyleImpl("annual-reviews")

private val annualReviewsAuthorDate: TBibliographyStyle =
        TBibliographyStyleImpl("annual-reviews-author-date")

private val associacaoBrasileiraDeNormasTecnicas: TBibliographyStyle =
        TBibliographyStyleImpl("associacao-brasileira-de-normas-tecnicas")

private val associationForComputingMachinery: TBibliographyStyle =
        TBibliographyStyleImpl("association-for-computing-machinery")

private val biomedCentral: TBibliographyStyle = TBibliographyStyleImpl("biomed-central")

private val bristolUniversityPress: TBibliographyStyle =
        TBibliographyStyleImpl("bristol-university-press")

private val britishMedicalJournal: TBibliographyStyle =
        TBibliographyStyleImpl("british-medical-journal")

private val cell: TBibliographyStyle = TBibliographyStyleImpl("cell")

private val chicagoAuthorDate: TBibliographyStyle = TBibliographyStyleImpl("chicago-author-date")

private val chicagoFullnotes: TBibliographyStyle = TBibliographyStyleImpl("chicago-fullnotes")

private val chicagoNotes: TBibliographyStyle = TBibliographyStyleImpl("chicago-notes")

private val copernicus: TBibliographyStyle = TBibliographyStyleImpl("copernicus")

private val councilOfScienceEditors: TBibliographyStyle =
        TBibliographyStyleImpl("council-of-science-editors")

private val councilOfScienceEditorsAuthorDate: TBibliographyStyle =
        TBibliographyStyleImpl("council-of-science-editors-author-date")

private val currentOpinion: TBibliographyStyle = TBibliographyStyleImpl("current-opinion")

private val deutscheGesellschaftFürPsychologie: TBibliographyStyle =
        TBibliographyStyleImpl("deutsche-gesellschaft-für-psychologie")

private val deutscheSprache: TBibliographyStyle = TBibliographyStyleImpl("deutsche-sprache")

private val elsevierHarvard: TBibliographyStyle = TBibliographyStyleImpl("elsevier-harvard")

private val elsevierVancouver: TBibliographyStyle = TBibliographyStyleImpl("elsevier-vancouver")

private val elsevierWithTitles: TBibliographyStyle = TBibliographyStyleImpl("elsevier-with-titles")

private val frontiers: TBibliographyStyle = TBibliographyStyleImpl("frontiers")

private val futureMedicine: TBibliographyStyle = TBibliographyStyleImpl("future-medicine")

private val futureScience: TBibliographyStyle = TBibliographyStyleImpl("future-science")

private val gb77142005Numeric: TBibliographyStyle = TBibliographyStyleImpl("gb-7714-2005-numeric")

private val gb77142015AuthorDate: TBibliographyStyle =
        TBibliographyStyleImpl("gb-7714-2015-author-date")

private val gb77142015Note: TBibliographyStyle = TBibliographyStyleImpl("gb-7714-2015-note")

private val gb77142015Numeric: TBibliographyStyle = TBibliographyStyleImpl("gb-7714-2015-numeric")

private val gostR7052008Numeric: TBibliographyStyle =
        TBibliographyStyleImpl("gost-r-705-2008-numeric")

private val harvardCiteThemRight: TBibliographyStyle =
        TBibliographyStyleImpl("harvard-cite-them-right")

private val instituteOfElectricalAndElectronicsEngineers: TBibliographyStyle =
        TBibliographyStyleImpl("institute-of-electrical-and-electronics-engineers")

private val instituteOfPhysicsNumeric: TBibliographyStyle =
        TBibliographyStyleImpl("institute-of-physics-numeric")

private val iso690AuthorDate: TBibliographyStyle = TBibliographyStyleImpl("iso-690-author-date")

private val iso690Numeric: TBibliographyStyle = TBibliographyStyleImpl("iso-690-numeric")

private val karger: TBibliographyStyle = TBibliographyStyleImpl("karger")

private val maryAnnLiebertVancouver: TBibliographyStyle =
        TBibliographyStyleImpl("mary-ann-liebert-vancouver")

private val modernHumanitiesResearchAssociation: TBibliographyStyle =
        TBibliographyStyleImpl("modern-humanities-research-association")

private val modernLanguageAssociation: TBibliographyStyle =
        TBibliographyStyleImpl("modern-language-association")

private val modernLanguageAssociation8: TBibliographyStyle =
        TBibliographyStyleImpl("modern-language-association-8")

private val multidisciplinaryDigitalPublishingInstitute: TBibliographyStyle =
        TBibliographyStyleImpl("multidisciplinary-digital-publishing-institute")

private val nature: TBibliographyStyle = TBibliographyStyleImpl("nature")

private val pensoft: TBibliographyStyle = TBibliographyStyleImpl("pensoft")

private val publicLibraryOfScience: TBibliographyStyle =
        TBibliographyStyleImpl("public-library-of-science")

private val royalSocietyOfChemistry: TBibliographyStyle =
        TBibliographyStyleImpl("royal-society-of-chemistry")

private val sageVancouver: TBibliographyStyle = TBibliographyStyleImpl("sage-vancouver")

private val sist02: TBibliographyStyle = TBibliographyStyleImpl("sist02")

private val spie: TBibliographyStyle = TBibliographyStyleImpl("spie")

private val springerBasic: TBibliographyStyle = TBibliographyStyleImpl("springer-basic")

private val springerBasicAuthorDate: TBibliographyStyle =
        TBibliographyStyleImpl("springer-basic-author-date")

private val springerFachzeitschriftenMedizinPsychologie: TBibliographyStyle =
        TBibliographyStyleImpl("springer-fachzeitschriften-medizin-psychologie")

private val springerHumanitiesAuthorDate: TBibliographyStyle =
        TBibliographyStyleImpl("springer-humanities-author-date")

private val springerLectureNotesInComputerScience: TBibliographyStyle =
        TBibliographyStyleImpl("springer-lecture-notes-in-computer-science")

private val springerMathphys: TBibliographyStyle = TBibliographyStyleImpl("springer-mathphys")

private val springerSocpsychAuthorDate: TBibliographyStyle =
        TBibliographyStyleImpl("springer-socpsych-author-date")

private val springerVancouver: TBibliographyStyle = TBibliographyStyleImpl("springer-vancouver")

private val taylorAndFrancisChicagoAuthorDate: TBibliographyStyle =
        TBibliographyStyleImpl("taylor-and-francis-chicago-author-date")

private val taylorAndFrancisNationalLibraryOfMedicine: TBibliographyStyle =
        TBibliographyStyleImpl("taylor-and-francis-national-library-of-medicine")

private val theInstitutionOfEngineeringAndTechnology: TBibliographyStyle =
        TBibliographyStyleImpl("the-institution-of-engineering-and-technology")

private val theLancet: TBibliographyStyle = TBibliographyStyleImpl("the-lancet")

private val thieme: TBibliographyStyle = TBibliographyStyleImpl("thieme")

private val trends: TBibliographyStyle = TBibliographyStyleImpl("trends")

private val turabianAuthorDate: TBibliographyStyle = TBibliographyStyleImpl("turabian-author-date")

private val turabianFullnote8: TBibliographyStyle = TBibliographyStyleImpl("turabian-fullnote-8")

private val vancouver: TBibliographyStyle = TBibliographyStyleImpl("vancouver")

private val vancouverSuperscript: TBibliographyStyle =
        TBibliographyStyleImpl("vancouver-superscript")

private val normal: TCiteFormAndMathClassClassAndRefFormAndTextStyle =
        TCiteFormAndMathClassClassAndRefFormAndTextStyleImpl("normal")

private val prose: TCiteForm = TCiteFormImpl("prose")

private val full: TCiteForm = TCiteFormImpl("full")

private val author: TCiteForm = TCiteFormImpl("author")

private val year: TCiteForm = TCiteFormImpl("year")

private val smooth: TCurveCloseModeAndImageScaling = TCurveCloseModeAndImageScalingImpl("smooth")

private val straight: TCurveCloseMode = TCurveCloseModeImpl("straight")

private val column: TFigureScope = TFigureScopeImpl("column")

private val parent: TFigureScope = TFigureScopeImpl("parent")

private val nonZero: TFillRule = TFillRuleImpl("non-zero")

private val evenOdd: TFillRule = TFillRuleImpl("even-odd")

private val cover: TImageFit = TImageFitImpl("cover")

private val contain: TImageFit = TImageFitImpl("contain")

private val stretch: TImageFit = TImageFitImpl("stretch")

private val png: TImageFormat = TImageFormatImpl("png")

private val jpg: TImageFormat = TImageFormatImpl("jpg")

private val gif: TImageFormat = TImageFormatImpl("gif")

private val svg: TImageFormat = TImageFormatImpl("svg")

private val pixelated: TImageScaling = TImageScalingImpl("pixelated")

private val punctuation: TMathClassClass = TMathClassClassImpl("punctuation")

private val opening: TMathClassClass = TMathClassClassImpl("opening")

private val closing: TMathClassClass = TMathClassClassImpl("closing")

private val fence: TMathClassClass = TMathClassClassImpl("fence")

private val large: TMathClassClass = TMathClassClassImpl("large")

private val relation: TMathClassClass = TMathClassClassImpl("relation")

private val unary: TMathClassClass = TMathClassClassImpl("unary")

private val binary: TMathClassClass = TMathClassClassImpl("binary")

private val vary: TMathClassClass = TMathClassClassImpl("vary")

private val a0: TPagePaper = TPagePaperImpl("a0")

private val a1: TPagePaper = TPagePaperImpl("a1")

private val a2: TPagePaper = TPagePaperImpl("a2")

private val a3: TPagePaper = TPagePaperImpl("a3")

private val a4: TPagePaper = TPagePaperImpl("a4")

private val a5: TPagePaper = TPagePaperImpl("a5")

private val a6: TPagePaper = TPagePaperImpl("a6")

private val a7: TPagePaper = TPagePaperImpl("a7")

private val a8: TPagePaper = TPagePaperImpl("a8")

private val a9: TPagePaper = TPagePaperImpl("a9")

private val a10: TPagePaper = TPagePaperImpl("a10")

private val a11: TPagePaper = TPagePaperImpl("a11")

private val isoB1: TPagePaper = TPagePaperImpl("iso-b1")

private val isoB2: TPagePaper = TPagePaperImpl("iso-b2")

private val isoB3: TPagePaper = TPagePaperImpl("iso-b3")

private val isoB4: TPagePaper = TPagePaperImpl("iso-b4")

private val isoB5: TPagePaper = TPagePaperImpl("iso-b5")

private val isoB6: TPagePaper = TPagePaperImpl("iso-b6")

private val isoB7: TPagePaper = TPagePaperImpl("iso-b7")

private val isoB8: TPagePaper = TPagePaperImpl("iso-b8")

private val isoC3: TPagePaper = TPagePaperImpl("iso-c3")

private val isoC4: TPagePaper = TPagePaperImpl("iso-c4")

private val isoC5: TPagePaper = TPagePaperImpl("iso-c5")

private val isoC6: TPagePaper = TPagePaperImpl("iso-c6")

private val isoC7: TPagePaper = TPagePaperImpl("iso-c7")

private val isoC8: TPagePaper = TPagePaperImpl("iso-c8")

private val dinD3: TPagePaper = TPagePaperImpl("din-d3")

private val dinD4: TPagePaper = TPagePaperImpl("din-d4")

private val dinD5: TPagePaper = TPagePaperImpl("din-d5")

private val dinD6: TPagePaper = TPagePaperImpl("din-d6")

private val dinD7: TPagePaper = TPagePaperImpl("din-d7")

private val dinD8: TPagePaper = TPagePaperImpl("din-d8")

private val sisG5: TPagePaper = TPagePaperImpl("sis-g5")

private val sisE5: TPagePaper = TPagePaperImpl("sis-e5")

private val ansiA: TPagePaper = TPagePaperImpl("ansi-a")

private val ansiB: TPagePaper = TPagePaperImpl("ansi-b")

private val ansiC: TPagePaper = TPagePaperImpl("ansi-c")

private val ansiD: TPagePaper = TPagePaperImpl("ansi-d")

private val ansiE: TPagePaper = TPagePaperImpl("ansi-e")

private val archA: TPagePaper = TPagePaperImpl("arch-a")

private val archB: TPagePaper = TPagePaperImpl("arch-b")

private val archC: TPagePaper = TPagePaperImpl("arch-c")

private val archD: TPagePaper = TPagePaperImpl("arch-d")

private val archE1: TPagePaper = TPagePaperImpl("arch-e1")

private val archE: TPagePaper = TPagePaperImpl("arch-e")

private val jisB0: TPagePaper = TPagePaperImpl("jis-b0")

private val jisB1: TPagePaper = TPagePaperImpl("jis-b1")

private val jisB2: TPagePaper = TPagePaperImpl("jis-b2")

private val jisB3: TPagePaper = TPagePaperImpl("jis-b3")

private val jisB4: TPagePaper = TPagePaperImpl("jis-b4")

private val jisB5: TPagePaper = TPagePaperImpl("jis-b5")

private val jisB6: TPagePaper = TPagePaperImpl("jis-b6")

private val jisB7: TPagePaper = TPagePaperImpl("jis-b7")

private val jisB8: TPagePaper = TPagePaperImpl("jis-b8")

private val jisB9: TPagePaper = TPagePaperImpl("jis-b9")

private val jisB10: TPagePaper = TPagePaperImpl("jis-b10")

private val jisB11: TPagePaper = TPagePaperImpl("jis-b11")

private val sacD0: TPagePaper = TPagePaperImpl("sac-d0")

private val sacD1: TPagePaper = TPagePaperImpl("sac-d1")

private val sacD2: TPagePaper = TPagePaperImpl("sac-d2")

private val sacD3: TPagePaper = TPagePaperImpl("sac-d3")

private val sacD4: TPagePaper = TPagePaperImpl("sac-d4")

private val sacD5: TPagePaper = TPagePaperImpl("sac-d5")

private val sacD6: TPagePaper = TPagePaperImpl("sac-d6")

private val isoId1: TPagePaper = TPagePaperImpl("iso-id-1")

private val isoId2: TPagePaper = TPagePaperImpl("iso-id-2")

private val isoId3: TPagePaper = TPagePaperImpl("iso-id-3")

private val asiaF4: TPagePaper = TPagePaperImpl("asia-f4")

private val jpShirokuBan4: TPagePaper = TPagePaperImpl("jp-shiroku-ban-4")

private val jpShirokuBan5: TPagePaper = TPagePaperImpl("jp-shiroku-ban-5")

private val jpShirokuBan6: TPagePaper = TPagePaperImpl("jp-shiroku-ban-6")

private val jpKiku4: TPagePaper = TPagePaperImpl("jp-kiku-4")

private val jpKiku5: TPagePaper = TPagePaperImpl("jp-kiku-5")

private val jpBusinessCard: TPagePaper = TPagePaperImpl("jp-business-card")

private val cnBusinessCard: TPagePaper = TPagePaperImpl("cn-business-card")

private val euBusinessCard: TPagePaper = TPagePaperImpl("eu-business-card")

private val frTellière: TPagePaper = TPagePaperImpl("fr-tellière")

private val frCouronneÉcriture: TPagePaper = TPagePaperImpl("fr-couronne-écriture")

private val frCouronneÉdition: TPagePaper = TPagePaperImpl("fr-couronne-édition")

private val frRaisin: TPagePaper = TPagePaperImpl("fr-raisin")

private val frCarré: TPagePaper = TPagePaperImpl("fr-carré")

private val frJésus: TPagePaper = TPagePaperImpl("fr-jésus")

private val ukBrief: TPagePaper = TPagePaperImpl("uk-brief")

private val ukDraft: TPagePaper = TPagePaperImpl("uk-draft")

private val ukFoolscap: TPagePaper = TPagePaperImpl("uk-foolscap")

private val ukQuarto: TPagePaper = TPagePaperImpl("uk-quarto")

private val ukCrown: TPagePaper = TPagePaperImpl("uk-crown")

private val ukBookA: TPagePaper = TPagePaperImpl("uk-book-a")

private val ukBookB: TPagePaper = TPagePaperImpl("uk-book-b")

private val usLetter: TPagePaper = TPagePaperImpl("us-letter")

private val usLegal: TPagePaper = TPagePaperImpl("us-legal")

private val usTabloid: TPagePaper = TPagePaperImpl("us-tabloid")

private val usExecutive: TPagePaper = TPagePaperImpl("us-executive")

private val usFoolscapFolio: TPagePaper = TPagePaperImpl("us-foolscap-folio")

private val usStatement: TPagePaper = TPagePaperImpl("us-statement")

private val usLedger: TPagePaper = TPagePaperImpl("us-ledger")

private val usOficio: TPagePaper = TPagePaperImpl("us-oficio")

private val usGovLetter: TPagePaper = TPagePaperImpl("us-gov-letter")

private val usGovLegal: TPagePaper = TPagePaperImpl("us-gov-legal")

private val usBusinessCard: TPagePaper = TPagePaperImpl("us-business-card")

private val usDigest: TPagePaper = TPagePaperImpl("us-digest")

private val usTrade: TPagePaper = TPagePaperImpl("us-trade")

private val newspaperCompact: TPagePaper = TPagePaperImpl("newspaper-compact")

private val newspaperBerliner: TPagePaper = TPagePaperImpl("newspaper-berliner")

private val newspaperBroadsheet: TPagePaper = TPagePaperImpl("newspaper-broadsheet")

private val presentation169: TPagePaper = TPagePaperImpl("presentation-16-9")

private val presentation43: TPagePaper = TPagePaperImpl("presentation-4-3")

private val even: TPagebreakTo = TPagebreakToImpl("even")

private val odd: TPagebreakTo = TPagebreakToImpl("odd")

private val simple: TParLinebreaks = TParLinebreaksImpl("simple")

private val optimized: TParLinebreaks = TParLinebreaksImpl("optimized")

private val document: TParLineNumberingScope = TParLineNumberingScopeImpl("document")

private val page: TParLineNumberingScopeAndRefForm = TParLineNumberingScopeAndRefFormImpl("page")

private val source: TPdfEmbedRelationship = TPdfEmbedRelationshipImpl("source")

private val `data`: TPdfEmbedRelationship = TPdfEmbedRelationshipImpl("data")

private val alternative: TPdfEmbedRelationship = TPdfEmbedRelationshipImpl("alternative")

private val supplement: TPdfEmbedRelationship = TPdfEmbedRelationshipImpl("supplement")

private val baseline: TTextBottomEdgeAndTextTopEdge = TTextBottomEdgeAndTextTopEdgeImpl("baseline")

private val descender: TTextBottomEdge = TTextBottomEdgeImpl("descender")

private val bounds: TTextBottomEdgeAndTextTopEdge = TTextBottomEdgeAndTextTopEdgeImpl("bounds")

private val lining: TTextNumberType = TTextNumberTypeImpl("lining")

private val oldStyle: TTextNumberType = TTextNumberTypeImpl("old-style")

private val proportional: TTextNumberWidth = TTextNumberWidthImpl("proportional")

private val tabular: TTextNumberWidth = TTextNumberWidthImpl("tabular")

private val italic: TTextStyle = TTextStyleImpl("italic")

private val oblique: TTextStyle = TTextStyleImpl("oblique")

private val ascender: TTextTopEdge = TTextTopEdgeImpl("ascender")

private val capHeight: TTextTopEdge = TTextTopEdgeImpl("cap-height")

private val xHeight: TTextTopEdge = TTextTopEdgeImpl("x-height")

private val thin: TTextWeight = TTextWeightImpl("thin")

private val extralight: TTextWeight = TTextWeightImpl("extralight")

private val light: TTextWeight = TTextWeightImpl("light")

private val regular: TTextWeight = TTextWeightImpl("regular")

private val medium: TTextWeight = TTextWeightImpl("medium")

private val semibold: TTextWeight = TTextWeightImpl("semibold")

private val bold: TTextWeight = TTextWeightImpl("bold")

private val extrabold: TTextWeight = TTextWeightImpl("extrabold")

private val black: TTextWeight = TTextWeightImpl("black")

public val allVariants: Map<String, TStr> = mapOf(
	"alphanumeric" to alphanumeric,
	"american-anthropological-association" to americanAnthropologicalAssociation,
	"american-chemical-society" to americanChemicalSociety,
	"american-geophysical-union" to americanGeophysicalUnion,
	"american-institute-of-aeronautics-and-astronautics" to
        americanInstituteOfAeronauticsAndAstronautics,
	"american-institute-of-physics" to americanInstituteOfPhysics,
	"american-medical-association" to americanMedicalAssociation,
	"american-meteorological-society" to americanMeteorologicalSociety,
	"american-physics-society" to americanPhysicsSociety,
	"american-physiological-society" to americanPhysiologicalSociety,
	"american-political-science-association" to americanPoliticalScienceAssociation,
	"american-psychological-association" to americanPsychologicalAssociation,
	"american-society-for-microbiology" to americanSocietyForMicrobiology,
	"american-society-of-civil-engineers" to americanSocietyOfCivilEngineers,
	"american-society-of-mechanical-engineers" to americanSocietyOfMechanicalEngineers,
	"american-sociological-association" to americanSociologicalAssociation,
	"angewandte-chemie" to angewandteChemie,
	"annual-reviews" to annualReviews,
	"annual-reviews-author-date" to annualReviewsAuthorDate,
	"associacao-brasileira-de-normas-tecnicas" to associacaoBrasileiraDeNormasTecnicas,
	"association-for-computing-machinery" to associationForComputingMachinery,
	"biomed-central" to biomedCentral,
	"bristol-university-press" to bristolUniversityPress,
	"british-medical-journal" to britishMedicalJournal,
	"cell" to cell,
	"chicago-author-date" to chicagoAuthorDate,
	"chicago-fullnotes" to chicagoFullnotes,
	"chicago-notes" to chicagoNotes,
	"copernicus" to copernicus,
	"council-of-science-editors" to councilOfScienceEditors,
	"council-of-science-editors-author-date" to councilOfScienceEditorsAuthorDate,
	"current-opinion" to currentOpinion,
	"deutsche-gesellschaft-für-psychologie" to deutscheGesellschaftFürPsychologie,
	"deutsche-sprache" to deutscheSprache,
	"elsevier-harvard" to elsevierHarvard,
	"elsevier-vancouver" to elsevierVancouver,
	"elsevier-with-titles" to elsevierWithTitles,
	"frontiers" to frontiers,
	"future-medicine" to futureMedicine,
	"future-science" to futureScience,
	"gb-7714-2005-numeric" to gb77142005Numeric,
	"gb-7714-2015-author-date" to gb77142015AuthorDate,
	"gb-7714-2015-note" to gb77142015Note,
	"gb-7714-2015-numeric" to gb77142015Numeric,
	"gost-r-705-2008-numeric" to gostR7052008Numeric,
	"harvard-cite-them-right" to harvardCiteThemRight,
	"institute-of-electrical-and-electronics-engineers" to
        instituteOfElectricalAndElectronicsEngineers,
	"institute-of-physics-numeric" to instituteOfPhysicsNumeric,
	"iso-690-author-date" to iso690AuthorDate,
	"iso-690-numeric" to iso690Numeric,
	"karger" to karger,
	"mary-ann-liebert-vancouver" to maryAnnLiebertVancouver,
	"modern-humanities-research-association" to modernHumanitiesResearchAssociation,
	"modern-language-association" to modernLanguageAssociation,
	"modern-language-association-8" to modernLanguageAssociation8,
	"multidisciplinary-digital-publishing-institute" to multidisciplinaryDigitalPublishingInstitute,
	"nature" to nature,
	"pensoft" to pensoft,
	"public-library-of-science" to publicLibraryOfScience,
	"royal-society-of-chemistry" to royalSocietyOfChemistry,
	"sage-vancouver" to sageVancouver,
	"sist02" to sist02,
	"spie" to spie,
	"springer-basic" to springerBasic,
	"springer-basic-author-date" to springerBasicAuthorDate,
	"springer-fachzeitschriften-medizin-psychologie" to springerFachzeitschriftenMedizinPsychologie,
	"springer-humanities-author-date" to springerHumanitiesAuthorDate,
	"springer-lecture-notes-in-computer-science" to springerLectureNotesInComputerScience,
	"springer-mathphys" to springerMathphys,
	"springer-socpsych-author-date" to springerSocpsychAuthorDate,
	"springer-vancouver" to springerVancouver,
	"taylor-and-francis-chicago-author-date" to taylorAndFrancisChicagoAuthorDate,
	"taylor-and-francis-national-library-of-medicine" to taylorAndFrancisNationalLibraryOfMedicine,
	"the-institution-of-engineering-and-technology" to theInstitutionOfEngineeringAndTechnology,
	"the-lancet" to theLancet,
	"thieme" to thieme,
	"trends" to trends,
	"turabian-author-date" to turabianAuthorDate,
	"turabian-fullnote-8" to turabianFullnote8,
	"vancouver" to vancouver,
	"vancouver-superscript" to vancouverSuperscript,
	"normal" to normal,
	"prose" to prose,
	"full" to full,
	"author" to author,
	"year" to year,
	"smooth" to smooth,
	"straight" to straight,
	"column" to column,
	"parent" to parent,
	"non-zero" to nonZero,
	"even-odd" to evenOdd,
	"cover" to cover,
	"contain" to contain,
	"stretch" to stretch,
	"png" to png,
	"jpg" to jpg,
	"gif" to gif,
	"svg" to svg,
	"pixelated" to pixelated,
	"punctuation" to punctuation,
	"opening" to opening,
	"closing" to closing,
	"fence" to fence,
	"large" to large,
	"relation" to relation,
	"unary" to unary,
	"binary" to binary,
	"vary" to vary,
	"a0" to a0,
	"a1" to a1,
	"a2" to a2,
	"a3" to a3,
	"a4" to a4,
	"a5" to a5,
	"a6" to a6,
	"a7" to a7,
	"a8" to a8,
	"a9" to a9,
	"a10" to a10,
	"a11" to a11,
	"iso-b1" to isoB1,
	"iso-b2" to isoB2,
	"iso-b3" to isoB3,
	"iso-b4" to isoB4,
	"iso-b5" to isoB5,
	"iso-b6" to isoB6,
	"iso-b7" to isoB7,
	"iso-b8" to isoB8,
	"iso-c3" to isoC3,
	"iso-c4" to isoC4,
	"iso-c5" to isoC5,
	"iso-c6" to isoC6,
	"iso-c7" to isoC7,
	"iso-c8" to isoC8,
	"din-d3" to dinD3,
	"din-d4" to dinD4,
	"din-d5" to dinD5,
	"din-d6" to dinD6,
	"din-d7" to dinD7,
	"din-d8" to dinD8,
	"sis-g5" to sisG5,
	"sis-e5" to sisE5,
	"ansi-a" to ansiA,
	"ansi-b" to ansiB,
	"ansi-c" to ansiC,
	"ansi-d" to ansiD,
	"ansi-e" to ansiE,
	"arch-a" to archA,
	"arch-b" to archB,
	"arch-c" to archC,
	"arch-d" to archD,
	"arch-e1" to archE1,
	"arch-e" to archE,
	"jis-b0" to jisB0,
	"jis-b1" to jisB1,
	"jis-b2" to jisB2,
	"jis-b3" to jisB3,
	"jis-b4" to jisB4,
	"jis-b5" to jisB5,
	"jis-b6" to jisB6,
	"jis-b7" to jisB7,
	"jis-b8" to jisB8,
	"jis-b9" to jisB9,
	"jis-b10" to jisB10,
	"jis-b11" to jisB11,
	"sac-d0" to sacD0,
	"sac-d1" to sacD1,
	"sac-d2" to sacD2,
	"sac-d3" to sacD3,
	"sac-d4" to sacD4,
	"sac-d5" to sacD5,
	"sac-d6" to sacD6,
	"iso-id-1" to isoId1,
	"iso-id-2" to isoId2,
	"iso-id-3" to isoId3,
	"asia-f4" to asiaF4,
	"jp-shiroku-ban-4" to jpShirokuBan4,
	"jp-shiroku-ban-5" to jpShirokuBan5,
	"jp-shiroku-ban-6" to jpShirokuBan6,
	"jp-kiku-4" to jpKiku4,
	"jp-kiku-5" to jpKiku5,
	"jp-business-card" to jpBusinessCard,
	"cn-business-card" to cnBusinessCard,
	"eu-business-card" to euBusinessCard,
	"fr-tellière" to frTellière,
	"fr-couronne-écriture" to frCouronneÉcriture,
	"fr-couronne-édition" to frCouronneÉdition,
	"fr-raisin" to frRaisin,
	"fr-carré" to frCarré,
	"fr-jésus" to frJésus,
	"uk-brief" to ukBrief,
	"uk-draft" to ukDraft,
	"uk-foolscap" to ukFoolscap,
	"uk-quarto" to ukQuarto,
	"uk-crown" to ukCrown,
	"uk-book-a" to ukBookA,
	"uk-book-b" to ukBookB,
	"us-letter" to usLetter,
	"us-legal" to usLegal,
	"us-tabloid" to usTabloid,
	"us-executive" to usExecutive,
	"us-foolscap-folio" to usFoolscapFolio,
	"us-statement" to usStatement,
	"us-ledger" to usLedger,
	"us-oficio" to usOficio,
	"us-gov-letter" to usGovLetter,
	"us-gov-legal" to usGovLegal,
	"us-business-card" to usBusinessCard,
	"us-digest" to usDigest,
	"us-trade" to usTrade,
	"newspaper-compact" to newspaperCompact,
	"newspaper-berliner" to newspaperBerliner,
	"newspaper-broadsheet" to newspaperBroadsheet,
	"presentation-16-9" to presentation169,
	"presentation-4-3" to presentation43,
	"even" to even,
	"odd" to odd,
	"simple" to simple,
	"optimized" to optimized,
	"document" to document,
	"page" to page,
	"source" to source,
	"data" to data,
	"alternative" to alternative,
	"supplement" to supplement,
	"baseline" to baseline,
	"descender" to descender,
	"bounds" to bounds,
	"lining" to lining,
	"old-style" to oldStyle,
	"proportional" to proportional,
	"tabular" to tabular,
	"italic" to italic,
	"oblique" to oblique,
	"ascender" to ascender,
	"cap-height" to capHeight,
	"x-height" to xHeight,
	"thin" to thin,
	"extralight" to extralight,
	"light" to light,
	"regular" to regular,
	"medium" to medium,
	"semibold" to semibold,
	"bold" to bold,
	"extrabold" to extrabold,
	"black" to black,
)
