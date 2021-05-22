package library.model;

import lombok.Getter;

import javax.xml.bind.annotation.XmlEnum;

@Getter
@XmlEnum
public enum Genre {
    CRIME,
    DETECTIVE,
    SCIENCE,
    FANTASY,
    ROMANCE,
    HORROR,
    CLASSIC,
    THRILLER,
    ANOTHER
}
